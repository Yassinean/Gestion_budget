package com.ba.budgetapp.controllers;

import com.ba.budgetapp.services.Impl.DashboardServiceImpl;
import com.ba.budgetapp.services.Interface.DashboardService;
import com.ba.budgetapp.services.Interface.ServiceFactory;
import com.ba.budgetapp.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardController {

    @FXML
    private Label totalIncomeLabel;

    @FXML
    private Label totalExpenseLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label transactionCountLabel;

    @FXML
    private PieChart expensePieChart;

    @FXML
    private BarChart<String, Number> monthlyBarChart;

    private final DashboardService dashboardService = ServiceFactory.dashboardService();

    @FXML
    public void initialize() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            // No user logged in; skip dashboard initialization
            return;
        }
        loadStatistics();
        loadPieChart();
        loadBarChart();
        System.out.println(SessionManager.getCurrentUser().getUsername());
    }

    private Long getCurrentUserId() {
        return SessionManager.getCurrentUserId();
    }

    private void loadStatistics() {
        Long userId = getCurrentUserId();
        if (userId == null) return;
        try {
            BigDecimal income = dashboardService.getTotalIncome(userId);
            BigDecimal expense = dashboardService.getTotalExpense(userId);
            BigDecimal balance = dashboardService.getCurrentBalance(userId);
            long count = dashboardService.getTransactionCount(userId);
            totalIncomeLabel.setText(income + " DH");
            totalExpenseLabel.setText(expense + " DH");
            balanceLabel.setText(balance + " DH");
            transactionCountLabel.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBarChart() {
        Long userId = getCurrentUserId();
        if (userId == null) return;
        try {
            monthlyBarChart.getData().clear();
            XYChart.Series<String, Number> incomeSeries =
                    new XYChart.Series<>();
            incomeSeries.setName("Revenus");
            dashboardService
                    .getMonthlyIncome(userId)
                    .forEach((month, amount) -> {
                        incomeSeries.getData().add(
                                new XYChart.Data<>(
                                        month,
                                        amount)
                        );
                    });

            XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
            expenseSeries.setName("Dépenses");

            dashboardService
                    .getMonthlyExpense(userId)
                    .forEach((month, amount) -> {

                        expenseSeries.getData().add(

                                new XYChart.Data<>(
                                        month,
                                        amount)
                        );
                    });

            monthlyBarChart.getData().add(incomeSeries);
            monthlyBarChart.getData().add(expenseSeries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPieChart() {
        Long userId = getCurrentUserId();
        if (userId == null) return;
        try {
            expensePieChart.getData().clear();
            Map<String, Double> expenses = dashboardService.getExpensesByCategory(userId);
            expenses.forEach((category, amount) -> {
                expensePieChart.getData().add(
                        new PieChart.Data(
                                category,
                                amount)
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
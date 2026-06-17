package com.ba.budgetapp.controllers;

import com.ba.budgetapp.models.entities.Budget;
import com.ba.budgetapp.services.Interface.BudgetService;
import com.ba.budgetapp.services.Interface.DashboardService;
import com.ba.budgetapp.services.Interface.ServiceFactory;
import com.ba.budgetapp.utils.SessionManager;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.util.List;

public class DashboardController {

    @FXML private Label totalIncomeLabel;
    @FXML private Label totalExpenseLabel;
    @FXML private Label balanceLabel;
    @FXML private Label transactionCountLabel;

    @FXML private PieChart expensePieChart;
    @FXML private BarChart<String, Number> monthlyBarChart;
    @FXML private ComboBox<Budget> budgetFilterCombo;

    @FXML private VBox incomeCard;
    @FXML private VBox expenseCard;
    @FXML private VBox balanceCard;
    @FXML private VBox countCard;
    Long budgetId = SessionManager.getCurrentBudgetId();

    private final DashboardService service = ServiceFactory.dashboardService();
    private final BudgetService budgetService = ServiceFactory.budgetService();

    @FXML
    public void initialize() {
        loadBudgetFilter();

        totalIncomeLabel.setText(service.getTotalIncome(budgetId) + " DH");
        totalExpenseLabel.setText(service.getTotalExpense(budgetId) + " DH");
        balanceLabel.setText(service.getCurrentBalance(budgetId) + " DH");
        transactionCountLabel.setText(String.valueOf(service.getTransactionCount(budgetId)));

        setLoadingState(true);

        applyHoverAnimation(incomeCard);
        applyHoverAnimation(expenseCard);
        applyHoverAnimation(balanceCard);
        applyHoverAnimation(countCard);
        loadDashboard();
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
        loadDashboard();
    }

    private void loadDashboard() {

        FadeTransition fade = new FadeTransition(Duration.millis(600));
        fade.setFromValue(0);
        fade.setToValue(1);

        loadCards();
        loadPie();
        loadBar();

        setLoadingState(false);
        
    }

    // ---------------- CARDS ----------------
    private void loadCards() {

        BigDecimal income = service.getTotalIncome(budgetId);
        BigDecimal expense = service.getTotalExpense(budgetId);
        BigDecimal balance = service.getCurrentBalance(budgetId);
        long count = service.getTransactionCount(budgetId);

        animateLabel(totalIncomeLabel, income + " DH");
        animateLabel(totalExpenseLabel, expense + " DH");
        animateLabel(balanceLabel, balance + " DH");
        animateLabel(transactionCountLabel, String.valueOf(count));
    }

    // ---------------- PIE ----------------
    private void loadPie() {
        expensePieChart.getData().clear();

        service.getExpensesByCategory(budgetId)
                .forEach((cat, val) ->
                        expensePieChart.getData().add(
                                new PieChart.Data(cat, val)
                        )
                );
    }

    // ---------------- BAR ----------------
    private void loadBar() {
        monthlyBarChart.getData().clear();

        XYChart.Series<String, Number> inc = new XYChart.Series<>();
        inc.setName("Income");

        service.getMonthlyIncome(budgetId)
                .forEach((m, v) ->
                        inc.getData().add(new XYChart.Data<>(m, v))
                );

        XYChart.Series<String, Number> exp = new XYChart.Series<>();
        exp.setName("Expense");

        service.getMonthlyExpense(budgetId)
                .forEach((m, v) ->
                        exp.getData().add(new XYChart.Data<>(m, v))
                );

        monthlyBarChart.getData().addAll(inc, exp);
    }

    private void loadBudgetFilter() {
        Long accountId = SessionManager.getCurrentAccountId();
        budgetFilterCombo.getItems().setAll(
                accountId == null ? List.of() : budgetService.findByOwnerId(accountId)
        );
        Budget currentBudget = SessionManager.getCurrentBudget();
        if (currentBudget != null) {
            budgetFilterCombo.setValue(currentBudget);
            budgetId = currentBudget.getBudgetId();
        } else if (!budgetFilterCombo.getItems().isEmpty()) {
            Budget firstBudget = budgetFilterCombo.getItems().get(0);
            budgetFilterCombo.setValue(firstBudget);
            budgetId = firstBudget.getBudgetId();
            SessionManager.setCurrentBudget(firstBudget);
        }

        budgetFilterCombo.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                budgetId = newValue.getBudgetId();
                SessionManager.setCurrentBudget(newValue);
                loadDashboard();
            }
        });
    }

    // ---------------- ANIMATIONS ----------------
    private void animateLabel(Label label, String text) {
        FadeTransition ft = new FadeTransition(Duration.millis(400), label);
        ft.setFromValue(0.3);
        ft.setToValue(1);
        label.setText(text);
        ft.play();
    }

    private void applyHoverAnimation(VBox card) {
        card.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        card.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });
    }

    // ---------------- LOADING SKELETON ----------------
    private void setLoadingState(boolean loading) {
        double opacity = loading ? 0.3 : 1;

        totalIncomeLabel.setOpacity(opacity);
        totalExpenseLabel.setOpacity(opacity);
        balanceLabel.setOpacity(opacity);
        transactionCountLabel.setOpacity(opacity);
    }
}

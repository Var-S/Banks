package Banks.Models;

public class BankSystem {
    private double _creditLimit;
    private final double _commission;
    private final double _yearPercent;
    private double _doubtfulClientLimit;
    private final double _lowPercent;
    private final double _mediumPercent;
    private final double _highPercent;

    public BankSystem(double creditLimit, double commission, double yearPercent,
                      double doubtfulClientLimit, double lowPercent, double mediumPercent, double highPercent) {
        _creditLimit = creditLimit;
        _commission = commission;
        _yearPercent = yearPercent;
        _doubtfulClientLimit = doubtfulClientLimit;
        _lowPercent = lowPercent;
        _mediumPercent = mediumPercent;
        _highPercent = highPercent;
    }

    public double getCommission() {
        return _commission;
    }

    public double getYearPercent() {
        return _yearPercent;
    }

    public double getLowPercent() {
        return _lowPercent;
    }

    public double getMediumPercent() {
        return _mediumPercent;
    }

    public double getHighPercent() {
        return _highPercent;
    }

    public double getDoubtfulClientLimit() {
        return _doubtfulClientLimit;
    }

    public double getCreditLimit() {
        return _creditLimit;
    }

    public double changeCreditLimit(double newLimit) {
        _creditLimit = newLimit;
        return newLimit;
    }

    public double changeDoubtfulClientLimit(double newLimit) {
        _doubtfulClientLimit = newLimit;
        return newLimit;
    }
}

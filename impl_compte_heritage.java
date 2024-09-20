public class impl_compte_heritage extends ComptePOA{

    double solde;

    impl_compte_heritage(double solde){
        this.solde = solde;
    }

    @Override
    public double solde() {
        return solde;
    }

    @Override
    public boolean credit(double amount) {
        if (amount >= 0) {
            this.solde = this.solde + amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean debit(double amount) {
        if (amount <= solde) {
            this.solde = this.solde - amount;
            return true;
        }
        return false;
    }
}

enum Roman {
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1);

    private int translation;


    Roman(int translation){
        this.translation = translation;
    }
    public int getTranslation(){
        return translation;
    }
}

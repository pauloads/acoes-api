package com.paulocorrea.desafiopagseguro.enums;

public enum TipoAcao {

    ORDINARIA("3"),
    PREFERENCIAL("4"),
    PREFERENCIAL_CLASSE_A("5"),
    PREFERENCIAL_CLASSE_B("6"),
    BDR_ETF_UNIT("11");

    private final String codigo;

    TipoAcao(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static TipoAcao obterPorCodigo(final String codigo){
        for(TipoAcao tipoAcao : TipoAcao.values()){
            if(tipoAcao.codigo.equals(codigo)){
                return tipoAcao;
            }
        }
        return null;
    }
}

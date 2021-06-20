package lib.Model.Material;

public class Material{
    
    private String nomeMaterial, areaConhecimento, tipoMaterial; 

    public String getNomeMaterial() {
        return this.nomeMaterial;
    }

    public void setNomeMaterial(String nomeMaterial) {
        this.nomeMaterial = nomeMaterial;
    }

    public String getAreaConhecimento() {
        return this.areaConhecimento;
    }

    public void setAreaConhecimento(String areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public String getTipoMaterial() {
        return this.tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public void cadastrarMaterial(String nomeMaterial, String areaConhecimento, String tipoMaterial){
        this.nomeMaterial = nomeMaterial;
        this.areaConhecimento = areaConhecimento;
        this.tipoMaterial = tipoMaterial;
    }

}

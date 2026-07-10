
package com.projeto.ufc.dto;

/**
 *
 * @author bruni
 */
public class ApiErrorDTO {
    
    private int status;
    private String erro;
    private String mensagem;
    
    public ApiErrorDTO(){
        
    }
    
    public ApiErrorDTO(int status, String erro, String mensagem){
        
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
    }
    
    public int getStatus(){
        return status;
    }
    
    public void setStatus(int status){
        this.status = status;
    }
    
    public String getErro(){
        return erro;
    }
    
    public void setErro(String erro){
        this.erro = erro;
    }
    
    public String getMensagem(){
        return mensagem;
    }
    
    public void setMensagem(String mensagem){
        this.mensagem = mensagem;
    }
}

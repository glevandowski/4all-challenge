package gustavo.levandowski.project4all.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/***
 * @author Gustavo Levandowski
 * @since 2018/06/25 00:05
 *
 */
public class User {

    @SerializedName("id")
    private String id_user;
    private String cidade;
    private String bairro;
    private String urlFoto;
    private String urlLogo;
    private String titulo;
    private String telefone;
    private String texto;
    private String endereco;
    private String latitude;
    private String longitude;

    @SerializedName("comentarios")
    private ArrayList<ListComments> comentarios = new ArrayList<>();

    public User() { }

    public User(String id_user, String cidade, String bairro, String urlFoto, String urlLogo, String titulo, String telefone, String texto, String endereco, String latitude, String longitude, ArrayList<ListComments> comentarios) {
        this.id_user = id_user;
        this.cidade = cidade;
        this.bairro = bairro;
        this.urlFoto = urlFoto;
        this.urlLogo = urlLogo;
        this.titulo = titulo;
        this.telefone = telefone;
        this.texto = texto;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comentarios = comentarios;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList<ListComments> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<ListComments> comentarios) {
        this.comentarios = comentarios;
    }
}

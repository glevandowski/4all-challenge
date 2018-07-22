package gustavo.levandowski.project4all.model;


/***
 * @author Gustavo Levandowski
 * Classe auxiliar para uso da model User, recebendo os parâmetros em uma lista
 */
public class ListComments{

    private String urlFoto;
    private String nome;
    private String nota;
    private String titulo;
    private String comentario;

    public ListComments(String urlFoto, String nome, String nota, String titulo, String comentario) {
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.nota = nota;
        this.titulo = titulo;
        this.comentario = comentario;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "{urlFoto:"+ urlFoto+
                ",nome:"+nome+
                ",nota:"+nota+
                ",titulo:"+titulo+
                ",comentario:"+comentario+
                "}";
    }
}

package gustavo.levandowski.project4all.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/***
 * @author Gustavo Levandowski
 * classe auxiliar para retorno de um array de string. {lista[]}
 */
public class ListTasks  {

    @SerializedName("lista")
    private ArrayList<String> lista;

    public ListTasks(ArrayList<String> lista) {
        this.lista = lista;
    }

    public ArrayList<String> getLista() {
        return lista;
    }
}

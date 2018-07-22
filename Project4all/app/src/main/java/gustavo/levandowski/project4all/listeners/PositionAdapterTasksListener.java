package gustavo.levandowski.project4all.listeners;

public interface PositionAdapterTasksListener {

    /***
     * Responsável por transitar os dados da view/adapter, ja no adapter servindo apenas como escuta recebendo comandos
     * de Responsabilidade da View.
     * Obs: classe idêntica à PositionAdapterCommentsListener, criado indepedentemente por questões de boas práticas em java.
     */
    void onLoadFragment(int position);
}

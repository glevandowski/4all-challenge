package gustavo.levandowski.project4all.listeners;


public interface PositionAdapterCommentsListener {
    /***
     * Responsável por transitar os dados da view/adapter, ja no adapter servindo apenas como escuta recebendo comandos
     * de Responsabilidade da View.
     * Obs: classe idêntica à PositionAdapterTasksListener, criado indepedentemente por questões de boas práticas em java.
     */
    void onLoadFragment(int position);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.gameconsole;

import com.moran.arngame.proteinsynthesizerlib.GameManager;
import com.moran.arngame.proteinsynthesizerlib.players.GamePlayer;
import com.moran.arngame.proteinsynthesizerlib.players.HumanPlayer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dmoranj
 */
public class GameConsole {

    private GameManager gm;
    private Map<String, GamePlayer> players= new HashMap<String, GamePlayer>();

    List<Command> commandTypes= new ArrayList<Command>();
    {
        commandTypes.add(new CommandJoin(this));
        commandTypes.add(new CommandNew(this));
        commandTypes.add(new CommandStart(this));
        commandTypes.add(new CommandList(this));
    }

    public static void main(String[] args) throws IOException {

        GameConsole gc = new GameConsole();
        gc.run();
    }

    private boolean started=false;

    private void run() throws IOException {
        boolean finished=false;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        mainLoop:while(!finished) {
            try {
                String prompt = "";
                if (getGm() != null && getGm().getCurrentPlayer() != null)
                    prompt = getGm().getCurrentPlayer();

                if (getGm() != null && started)
                    prompt += "(" + gm.getCurrentChainLength() + ")";
                
                System.out.print("\n" + prompt + "> ");
                String line = br.readLine().trim().replaceAll("\\d+", " ");

                // Process input
                if (line.equals("help")) {
                    showHelp();
                    continue mainLoop;
                } else if (line.equals("quit")) {
                    finished = true;
                    continue mainLoop;
                } else if (line.equals(""))
                    continue;
                else {
                    for (Command command : commandTypes) {
                        if (command.match(line)) {
                            continue mainLoop;
                        }
                    }
                }

                // Play the game
                if (started) {
                    for (GamePlayer gp : players.values()) {
                        if (gp instanceof HumanPlayer) {
                            ((HumanPlayer) gp).setCurrentString(line);
                        }
                    }

                    play();
                } else {
                    System.out.println("Syntax error: use 'help' for valid commands.");
                }

            } catch (Throwable ex) {
                System.out.println("Ooops! " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void showHelp() {
        System.out.println("Command list:\n");
        System.out.println("-----------------------------------------------------------------");
        for (Command command: commandTypes) {
            System.out.println("> " + command.getSyntax());
            System.out.println("\t" + command.getDescription() + "\n");
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("\n");
    }

    /**
     * @return the Game Manager
     */
    public GameManager getGm() {
        return gm;
    }

    /**
     * @param GameManager to set
     */
    public void setGm(GameManager gm) {
        this.gm = gm;
    }

    /**
     * @return The players
     */
    public Map<String, GamePlayer> getPlayers() {
        return players;
    }

    /**
     * @param Players to set
     */
    public void setPlayers(Map<String, GamePlayer> players) {
        this.players = players;
    }

    void win(String winner) {
        System.out.println("Congratulations, there is a winner: " + winner + "!!");
        started=false;
    }

    /**
     * @return the started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @param started the started to set
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    private void play() throws Exception {

        do {

            String issuedChain = players.get(gm.getCurrentPlayer()).issueChain(gm.getCurrentChainLength());
            System.out.println("Player " + gm.getCurrentPlayer() + " issues the chain " + issuedChain);
            String result = gm.issueChain(gm.getCurrentPlayer(), issuedChain.toUpperCase());

            if (result!=null) {
                win(result);
                break;
            }

        } while (!"human".equals(players.get(gm.getCurrentPlayer()).getType()));
    }
}

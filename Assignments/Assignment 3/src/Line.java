import java.util.logging.Logger;

public class Line {
    volatile private static Logger LOGGER = Logger.getLogger("global");
    public int numLines;
    public volatile ATM atm;
    public volatile CardUser [] lines;

    public Line(int numLines, ATM atm) {
        this.numLines = numLines;
        this.atm = atm;
        lines = new CardUser[numLines];
        for(int i=0; i<numLines; i++) {
            lines[i] = new CardUser(atm);
        }
    }
    public void setLines(CardUser [] lines) {
        this.lines = lines;
    }

    public CardUser [] getLines() {
        return lines;
    }

    public void run() {
            for (int i = 0; i < numLines; i++) {
                lines[i].start();
            }
    }
}


public class PerformanceTest {
	
    //static int perftTotalMoveCounter=0;
    static int perftMoveCounter=0;
    static int perftMaxDepth=5;
    public static String moveToAlgebra(String move)
    {
        String moveString="";
        moveString+=""+(char)(move.charAt(1)+49);
        moveString+=""+('8'-move.charAt(0));
        moveString+=""+(char)(move.charAt(3)+49);
        moveString+=""+('8'-move.charAt(2));
        return moveString;
    }
    public static void perft(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,int depth)
    {
        if (depth<perftMaxDepth) {
            String moves;
            if (WhiteToMove) {
                moves=Moves.possibleMovesW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
            } else {
                moves=Moves.possibleMovesB(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
            }
            for (int i=0;i<moves.length();i+=4) {
                long WPt=Moves.makeMove(WP, moves.substring(i,i+4), 'P'), WNt=Moves.makeMove(WN, moves.substring(i,i+4), 'N'),
                        WBt=Moves.makeMove(WB, moves.substring(i,i+4), 'B'), WRt=Moves.makeMove(WR, moves.substring(i,i+4), 'R'),
                        WQt=Moves.makeMove(WQ, moves.substring(i,i+4), 'Q'), WKt=Moves.makeMove(WK, moves.substring(i,i+4), 'K'),
                        BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'p'), BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'n'),
                        BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'b'), BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'r'),
                        BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'q'), BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'k'),
                        EPt=Moves.makeMoveEP(WP|BP,moves.substring(i,i+4));
                boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
                if (Character.isDigit(moves.charAt(3))) {//'regular' move
                    int start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                    if (((1L<<start)&WK)!=0) {CWKt=false; CWQt=false;}
                    if (((1L<<start)&BK)!=0) {CBKt=false; CBQt=false;}
                    if (((1L<<start)&WR&(1L<<63))!=0) {CWKt=false;}
                    if (((1L<<start)&WR&(1L<<56))!=0) {CWQt=false;}
                    if (((1L<<start)&BR&(1L<<7))!=0) {CBKt=false;}
                    if (((1L<<start)&BR&1L)!=0) {CBQt=false;}
                }
                if (((WKt&Moves.unsafeForWhite(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt))==0 && WhiteToMove) ||
                        ((BKt&Moves.unsafeForBlack(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt))==0 && !WhiteToMove)) {
                    if (depth+1==perftMaxDepth) {perftMoveCounter++;}
                    perft(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt,!WhiteToMove,depth+1);
                }
            }
        }
    }
}

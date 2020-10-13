class Board{
    static final int BLACK=1;
    static final int WHITE=-1;
    static final int EMPTY=0;
    static final int WALL=2;
    static final int WIDTH=8;
    static final int HEIGHT=8;
    int board_state[][] = new int[8][8];    //盤面の状態
    int current_turn;    //現在の手番
    int current_cnt;    //現在の手数

    Board(){
	for(int i=0;i<WIDTH;i++)
	    for(int j=0;j<HEIGHT;j++)
		board_state[i][j]=0;
	current_turn=BLACK;
	current_cnt=0;
    }
	
    Board(int[][] board1,int currentColor1,int turns1){
	board_state=board1;
	current_turn=currentColor1;
	current_cnt=turns1;
    }

    int getCurrColor(){
	return current_turn;
    }
	
    void setCurrColor(int color){
	this.current_turn=color;
    }
	
    void changeCurrColor(){
	if(current_turn==WHITE)current_turn=BLACK;
	else if(current_turn==BLACK)current_turn=WHITE;
	current_cnt++;
    }
	
    int getTurns(){
	return current_turn;
    }
	
    void setTurns(int num){
	this.current_cnt=num;
    }

    int getDisc(int x,int y){
	return board_state[x][y];
    }

    void setDisc(int x,int y,int disc){
	this.board_state[x][y]=disc;
    }

    void init(){
	for(int i=0;i<8;i++)
	    for(int j=0;j<8;j++)
		board_state[i][j] = EMPTY;
	board_state[3][3] = BLACK;
	board_state[3][4] = WHITE;
	board_state[4][3] = WHITE;
	board_state[4][4] = BLACK;
	current_cnt = 0;
	current_turn = BLACK;
    }

    boolean putDisc(int x,int y,boolean dataUpdate){
	int xx;
	int yy;
	int cnt; //一個以上隣接する逆の色が繋がっているかどうか
	int color=getCurrColor();
	boolean put=false;
	//[縦方向][横方向]
	int dir[][]={{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};
	//そこが空かどうか
	//System.out.println(color);
	//System.out.println("x="+x+"y="+y);
	if(x<0 || x>7 || y<0 || y>7){
	    return false;
	}
	if(board_state[x][y]==EMPTY){
	    for(int i=0;i<8;i++){//8方向繰り返す
		xx=x;
		yy=y;
		cnt=0;
		//System.out.println(i+"========================================");
		while(true){
		    xx=xx+dir[i][0];//xをi方向に動かした位置
		    yy=yy+dir[i][1];//yを…   
		    if(xx<0 || xx>7 || yy<0 || yy>7){
			//System.out.println("kabenasi");

			break;//壁があるかどうか
		    }
		    //その方向に自分と逆の色の駒が一個以上あるか
		    if(board_state[xx][yy]==(-color)){
			cnt++;
			//逆の色では無いマスに辿り着いた時にそれまでに一個以上隣接している逆の色があって次の色が自分と同じ色かどうか
			//System.out.println("xx="+xx+" yy="+yy+" put=true cnt="+cnt);
		    }else if(cnt!=0 && board_state[xx][yy]==color ){
			put=true;
			if(dataUpdate==true){
			    xx=x;
			    yy=y;
			    for(;cnt>=0;cnt--){
				board_state[xx][yy]=color;
				xx=xx+dir[i][0];
				yy=yy+dir[i][1];

			    }
			}
			break;
		    }
		    else{
			break;
		    }
		}
	    }
	    if(dataUpdate==true && put==true)
		changeCurrColor();
	}
	return put;	
    }

    boolean checkPass(){
	for(int i=0;i<8;i++)
	    for(int j=0;j<8;j++)
		if(board_state[i][j]==EMPTY) 
		    if(putDisc(i,j,false)==true){
			return false;
		    }
	return true;
    }

    Board copy(){
	int[][] cp = new int [8][8];
	for(int i = 0;i < 8;i++){
	    for(int j=0;j < 8;j++){
		cp[i][j] = board_state[i][j];
	    }
	}
	Board board_copy = new Board(cp,current_turn,current_cnt);
	return board_copy;
    }

    int eval(int color){
	int evaluation_value=0;
	int eval_board[][] = {{100,-30,20,30,30,20,-30,100},{-30,-40,0,10,10,0,-40,-30},{20,0,50,70,70,50,0,20},{30,10,70,0,0,70,10,30},{30,10,70,0,0,70,10,30},{20,0,50,70,70,50,0,20},{-30,-40,0,10,10,0,-40,-30},{100,-30,20,30,30,20,-30,100}};
	/*if(color == BLACK)
	    evaluation_value = getDiscSum(BLACK) - getDiscSum(WHITE);
	else{
	    evaluation_value = getDiscSum(WHITE) - getDiscSum(BLACK);
	    }*/
	for(int i=0;i < 8;i++){
	    for(int j=0;j < 8;j++){
		if(i != 0 && j != 0){
		    if(eval_board[i-1][j-1] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(i != 0){
		    if(eval_board[i-1][j] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(i != 0 && j != 7){
		    if(eval_board[i-1][j+1] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(j != 0){
		    if(eval_board[i][j-1] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(j != 7){
		    if(eval_board[i][j+1] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(i != 7 && j != 0){
		    if(eval_board[i+1][j-1] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(i != 7) {
		    if(eval_board[i+1][j] == EMPTY) {
			eval_board[i][j] -= 10;
		    }
		}
		if(i != 7 && j != 7){
		    if(eval_board[i+1][j+1] == EMPTY) {
			eval_board[i][j] -= 10;
		    }    
		}
		evaluation_value += board_state[i][j]*eval_board[i][j];
	    }
	}
	return getCurrColor()*evaluation_value;
	//return evaluation_value;
    }

    int getDiscSum(int color){
	int cnt_of_piece = 0;    //駒の数
	for(int i=0;i<8;i++)
	    for(int j=0;j<8;j++)
		if(getDisc(i,j)==color)
		    cnt_of_piece++;
	return cnt_of_piece;
    }

    void display(){
	System.out.printf("   0   1   2   3   4   5   6   7  \n");
	for(int i=0;i<8;i++){
	    System.out.printf("  ╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋\n");
	    System.out.print(" "+i+"｜");
	    for(int j=0;j<8;j++){
		if(getDisc(i,j)==BLACK)    //i行j列が黒ならば
		    System.out.print("⚫ ｜");
		else if(getDisc(i,j)==WHITE)    //i行j列が白ならば
		    System.out.print("⚪ ｜");
		else{    //i行j列が空白ならば
		    System.out.print("  ｜");
		}
	    }
	    System.out.println();
	}
	System.out.printf("  ╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋━━━╋\n");
	System.out.print("現在の手番:"+current_turn+"  手数:"+current_cnt+"  黒の駒数:"+getDiscSum(BLACK)+"  白の駒数:"+getDiscSum(WHITE));
    }
    
}	

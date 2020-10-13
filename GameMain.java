import java.util.Scanner;
class GameMain{
    static int node;
    static long nodeTotal[]=new long[2],timeTotal[]=new long[2];
    public static void main(String args[]){
	Board b=new Board();
	b.init();
	int pos[]=null;
	int passCount=0;
	int limit = 4;
	while(passCount<2){  //パスが２回未満
	    if(!(b.checkPass())){	//パスではない
		b.display();
		node=0;
		do{
		    System.out.println("Please input position.");
		    long start = System.currentTimeMillis();
		    long end=start;
		    if(b.getCurrColor()==Board.BLACK){	//黒の手番
			
			pos=humanPlayer();	//CPUプレイにしたい場合はここを変更してコンピュータプレイヤーのメソッドを呼び出す
			//pos = root(limit,b,b.getCurrColor());
			
			end = System.currentTimeMillis();
			nodeTotal[0]+=node;
			timeTotal[0]+=(end-start);
		    }else if(b.getCurrColor()==Board.WHITE){	//白の手番
			
			//pos=humanPlayer();	//CPUプレイにしたい場合はここを変更してコンピュータプレイヤーのメソッドを呼び出す
			pos = root(limit,b,b.getCurrColor());
			end = System.currentTimeMillis();
			nodeTotal[1]+=node;
			timeTotal[1]+=(end-start);
		    }
		    System.out.println((end-start)+" msec");
		    System.out.println(node+" nodes");
		    System.out.println("("+pos[0]+","+pos[1]+")");
			//	if(!(b.putDisc(pos[0], pos[1], true))) throw new RuntimeException("Invalid position.");	//指定の場所に駒を置けない場所の場合は中断
		    }while(!(b.putDisc(pos[0],pos[1],true)));
		passCount=0;
	    }else {passCount++;b.changeCurrColor();}	//パスの場合
	}
	b.display();
	System.out.println("Black's Total Number of Nodes:"+nodeTotal[0]);
	System.out.println("Black's Total Time:"+timeTotal[0]);
	System.out.println("White's Total Number of Nodes:"+nodeTotal[1]);
	System.out.println("White's Total Time:"+timeTotal[1]);
    }
    
    static int[] humanPlayer(){
	return input(2);	//playerは２つの整数を入力して配列で返す
    }
    
    static int[] root(int limit,Board b,int color) {
	Board bb = b.copy();
	int score,score_max = -Integer.MAX_VALUE;
	int pos[] = new int[2];
	
	for(int i = 0;i<8;i++)		//盤面をfor文で走査
	    for(int j = 0;j<8;j++)
		if(bb.putDisc(i,j,true) == true) {	//駒を置ける場所ならば
		    score = minlevel(limit-1,bb,color);

		    if(score > score_max) {

			score_max = score;
			pos[0] = i;
			pos[1] = j;
		    }
		    bb = b.copy();
		}
	return pos;
    }

    static int maxlevel(int limit,Board b,int color) {
	int flag = 0;
	int score;
	if(limit == 0)
 	    return b.eval(color);

	Board bb = b.copy();

	int score_max = -Integer.MAX_VALUE;

	for(int i=0;i<8;i++)
	    for(int j=0;j<8;j++)
		if(bb.putDisc(i,j,true) == true) {
		    flag = 1;
		    score = minlevel(limit-1,bb,color);
		    if(score > score_max)
			score_max = score;
		    bb = b.copy();
		}
	if(flag == 0){
	    bb.changeCurrColor();
	    score = minlevel(limit-1,bb,color);
	    if(score > score_max)
		score_max = score;
	}
	return score_max;
  
    }

    static int minlevel(int limit,Board b,int color) {
	int flag = 0;
	int score;
	if(limit == 0)
	    return b.eval(color);
	
	Board bb = b.copy();

	int score_min = Integer.MAX_VALUE;
	
	for(int i=0;i<8;i++)
	    for(int j=0;j<8;j++)
		if(bb.putDisc(i,j,true) == true) {
		    flag = 1;
		    score = maxlevel(limit-1,bb,color);
		    if(score < score_min)
			score_min = score;
		    bb = b.copy();
		}

	if(flag == 0){
	    bb.changeCurrColor();
	    score = maxlevel(limit-1,bb,color);
	    if(score < score_min)
		score_min = score;
	}
	return score_min;
    }
	
    static int[] input(int num){
	int array[] = new int[num];
	Scanner scan = new Scanner(System.in);
	for(int i=0;i<array.length;i++) array[i] = scan.nextInt();
	return array;
    }   
}

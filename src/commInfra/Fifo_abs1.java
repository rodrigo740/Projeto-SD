package commInfra;

public class Fifo_abs1 <T> extends Fifo_abstract1 <T>{
    
	private int size;
    private Object stk[];
    private Object aux[];
    private int position;
    public Fifo_abs1(int size) {
		
		this.size = size;
        position=0;
        stk= new Object[size];
	}
    @Override
    public void add (Object letter){
        
        try {
   		 	stk[position] = letter;
            position++;
	   	}catch(Exception E) {
	   		System.out.println("FIFO is full");
	   		insert();
	   	}
    }
    @Override
    public Object remove(){
        /*if(size<=0){
            System.out.println("FIFO is empty");
            return ' ';
        }

        Object read = stk[0];
        for (int i = 0;i<size-1;i++){
            stk[i]= stk[i+1];
        }
        position--;
        return read;*/
        
        
       
        try {
        	Object read = stk[0];
        	for (int i = 0;i<size-1;i++){
                stk[i]= stk[i+1];
            }
        	position--;
        	return read;
        }catch(Exception E) {
        	
        	System.out.println("FIFO is empty");
        	return 0;
        }
    }
    
    @Override
    public void insert() {
    	aux = new Object[size+1];
    	for (int i = 0;i<(int)size();i++) {
    		aux[i] = stk[i]; 
    	}
    	stk=aux;
    }
    
    @Override
    public Object size(){
        return stk.length;
    }
}
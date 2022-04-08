package commInfra;

public class StackChar_abs1<T> extends StackChar_abstrac1 <T> {
    
	private int size;
    private Object stk[];
    private int position;
    private Object aux[];
    public StackChar_abs1(int size) {
		
		this.size = size;
        position=0;
        stk= new Object[size];
	}
    @Override
    public void push(Object letter){
       // if(position<size){
    	try {
    		 stk[position] = letter;
             position++;
    	}catch(Exception E) {
    		System.out.println("Stack is full");
    		insert();
    	}
           
    }
    
    @Override
    public Object pop(){
        position--;
        try {
        	Object read = stk[position];
        	return read;
        }catch(Exception E) {
        	position++;
        	System.out.println("Stack is empty");
        	return 0;
        }
               
    }
    
    public Object get(int i) {
    	return stk[i];
    }
    
    //inserir mais espaco no array
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
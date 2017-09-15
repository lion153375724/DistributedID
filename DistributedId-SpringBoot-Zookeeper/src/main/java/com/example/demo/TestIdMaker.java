package com.example.demo;

import java.util.HashSet;

import com.example.demo.IdMaker.RemoveMethod;

public class TestIdMaker {
	/*public static void main(String[] args) throws Exception {
        
        IdMaker idMaker = new IdMaker("10.17.1.234:2181,10.17.1.235:2181,10.17.1.236:2181",
                "/NameService/IdGen", "ID-");
        idMaker.start();
        try {
            for (int i = 0; i < 2; i++) {
                String id = idMaker.generateId(RemoveMethod.DELAY);
                System.out.println(id);
            }
        } finally {
            idMaker.stop();
        }
    }*/
	
	public static void main(String[] args) throws Exception {
		
		
		IdMaker idMaker = new IdMaker("10.17.1.234:2181,10.17.1.235:2181,10.17.1.236:2181",
                "/NameService/IdGen", "ID-");
        idMaker.start();
        try {
        	for(int i =0 ;i<2000;i++){
            	new Thread(new zkMarkId(idMaker)).start();;
            }
		} catch (Exception e) {
		} finally {
            //idMaker.stop();
        }
	}
	
	static class zkMarkId implements Runnable{
		private HashSet<String> idSet = new HashSet<String>();
		
		private IdMaker idMaker;
		public zkMarkId(IdMaker idMaker){
			this.idMaker = idMaker;
		}
		@Override
		public void run() {
			String id;
			try {
				id = idMaker.generateId(RemoveMethod.DELAY);
				System.out.println(id);
				if(!idSet.add(id)){
					System.out.println(("重复id:" + id));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
			
		}
		
	}
}

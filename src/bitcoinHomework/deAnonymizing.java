package bitcoinHomework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class deAnonymizing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//10000054
		try {
			FileReader inputFile=new FileReader("F:\\hw2\\txin.dat");
			BufferedReader bufIn=new BufferedReader(inputFile);
			String lineJustFetched=null;
			String [] arr;
			HashMap<Long,HashSet<Long>> inputMap=new HashMap();
			HashSet<String> entitySet=new HashSet();
			HashMap<Long,Long> addressDumps=new HashMap();
 			
			while(true) {
				lineJustFetched=bufIn.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					if(txId>=10000055)
						break;
					long addr=Long.parseLong(arr[4]);
					HashSet temp=inputMap.getOrDefault(txId,new HashSet());
					temp.add(addr);
					inputMap.put(txId,temp);
					addressDumps.put(addr,txId);
				}
			}
			bufIn.close();
			System.out.println(inputMap.size());
			
			FileReader txOutFile=new FileReader("\\resource\\txout.dat");
			BufferedReader bufTxOut=new BufferedReader(txOutFile);
			long prevTx=0l;
			List<Long> li=new ArrayList();
			
			while(true) {
				lineJustFetched=bufTxOut.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					
					if(txId>=10000055)
						break;
					if(prevTx==0)
						prevTx=txId;
					if(prevTx!=txId) {
						if(li.size()==1) {
						HashSet temp=inputMap.get(addressDumps.get(li.get(1)));
						temp.add(li.get(1));
						inputMap.put(addressDumps.get(li.get(1)),temp);
						addressDumps.put(li.get(1),txId);
					}
						else if(li.size()>1) {
							HashSet t=inputMap.get(prevTx);
							for(long e:li) {
								if(!addressDumps.containsKey(e))
									t.add(e);
							}
							inputMap.put(prevTx,t);						
						}
						li.clear();
						prevTx=txId;
						li.add(Long.parseLong(arr[2]));
				}
					else
						li.add(Long.parseLong(arr[2]));
			}
			
			}
			long l=1056438992951183l;
			System.out.println("Total Number of users in dataset :"+inputMap.size());
			
			System.out.println("Average balance per user: "+l/inputMap.size());
			
			System.out.println("Average input transaction per user :"+20168554/inputMap.size());
			
			System.out.println("Average output transaction per user :"+23266807/inputMap.size());
			
			
			
			
		}catch(Exception e) {
			
		}
	}

}

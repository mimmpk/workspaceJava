package demo;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class test {

	public static void main(String[] args) {
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		
		//Server
		env.put(Context.PROVIDER_URL, "ldap://10.252.169.13:6801"); 
		env.put(Context.REFERRAL, "ignore");
		
		//Authentication
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, "dc=C-NTDB");
	    env.put(Context.SECURITY_CREDENTIALS, "secret");
	    
	    DirContext ctx = null;
	    try{
	       ctx = new InitialDirContext(env);
	       System.out.println("Success connect LDAP Server!!");
	    }catch(NamingException e){
	    	System.out.println("Cannot connect LDAP Server!! \nMessage: " + e.getMessage());
	    }
	    
	    NamingEnumeration<SearchResult> results = null;
	    try {
	    	SearchControls controls = new SearchControls();
	    	controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    	controls.setCountLimit(100);
	    	controls.setTimeLimit(5000);
	    	
	    	//Search Filter
	    	results = ctx.search("subdata=profile,ds=amf,subdata=services,uid=869111107770000,ds=SUBSCRIBER,o=AIS,dc=C-NTDB", 
	    			"objectClass=amfCounterEntry", controls); 
	    	
	    	int i = 0;
	    	while(results.hasMoreElements()){
	    		SearchResult result = (SearchResult)results.next();
	    		System.out.print(++i + " ");
	    		System.out.println(result);
	    	}
	    	ctx.close();
	    }catch(NamingException e){
	    	e.printStackTrace();
	    }
	    
	}
}

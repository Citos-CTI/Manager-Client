/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johannes.lsctic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author johannesengler
 */
public class LDAPController {

    private Hashtable env;
    private String ldapUrl;
    private DirContext dctx;
    private String base;

    public LDAPController(String serverIp, int port, String dc, String dc2, String ou) {
        env = new Hashtable();

        String sp = "com.sun.jndi.ldap.LdapCtxFactory";
        env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

        ldapUrl = "ldap://" + serverIp + ":" + port + "/dc=" + dc + " , dc=" + dc2;

        env.put(Context.PROVIDER_URL, ldapUrl);


        base = "ou=" + ou;
    }

    public LDAPController(String serverIp, int port, String dc, String ou) {
        env = new Hashtable();

        String sp = "com.sun.jndi.ldap.LdapCtxFactory";
        env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

        ldapUrl = "ldap://" + serverIp + ":" + port + "/dc=" + dc;

        env.put(Context.PROVIDER_URL, ldapUrl);

        
        base = "ou=" + ou;
    }

    
    public ArrayList<LDAPEntry> getN(String ein, int n, ArrayList<String> fieldsToSearch) {
        ArrayList<LDAPEntry> aus = new ArrayList<>();
        SearchControls sc = new SearchControls();
        String[] attributeFilter = {"cn", "mail", "sn", "givenName", "l","mobile","telephoneNumber","o" };
        sc.setReturningAttributes(attributeFilter);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(|(sn="+ein+"*)(sn="+ein+"*)(cn="+ein+"*)(o="+ein+"*))";

        NamingEnumeration results = null;
        
        dctx = null;
        try {
            dctx = new InitialDirContext(env);
        } catch (NamingException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            results = dctx.search(base, filter, sc);
        } catch (NamingException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            int i = 0;
            while (results.hasMore() && i<n) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();
                
                ArrayList<String> data = new ArrayList<>();
                
                
                for(String field: fieldsToSearch){
                    Attribute attr = (Attribute) attrs.get(field);
                    data.add((String) attr.get());
                } 
                aus.add(new LDAPEntry(data, data.get(0)));
                ++i;
            }
        } catch (NamingException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dctx.close();
        } catch (NamingException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return aus;
    }
}
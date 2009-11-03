/**
 *
 * Copyright 1997-2005 Unidata Program Center/University Corporation for
 * Atmospheric Research, P.O. Box 3000, Boulder, CO 80307,
 * support@unidata.ucar.edu.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ucar.unidata.repository.ftp;




import ucar.unidata.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import java.io.IOException;

import org.apache.ftpserver.*;
import org.apache.ftpserver.listener.*;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.*;
import org.apache.ftpserver.usermanager.impl.*;




/**
 *
 *
 * @author IDV Development Team
 * @version $Revision: 1.3 $
 */

public class RepositoryFtpUserManager implements org.apache.ftpserver.ftplet.UserManager {
    FtpManager ftpManager;
    BaseUser user;

    public RepositoryFtpUserManager(FtpManager ftpManager) {
	this.ftpManager = ftpManager;
	user = new BaseUser();
	user.setName("test");
	user.setPassword("test");
	user.setHomeDirectory("/Users/jeffmc");
	List<Authority> auths = new ArrayList<Authority>();
	auths.add(new ConcurrentLoginPermission(10,10));
	//	    Authority auth = new WritePermission();
	//	    auths.add(auth);
	user.setAuthorities(auths);
    }

    public org.apache.ftpserver.ftplet.User authenticate(Authentication auth) {
	if(true) return user;
	/*
	  if(auth instanceof UsernamePasswordAuthentication) {
	  UsernamePasswordAuthentication upa = (UsernamePasswordAuthentication) auth;
	  System.err.println ("auth:" +upa.getUsername() +" " + upa.getPassword());
	  if(user == null) {
	  user = new RepositoryFtpUser(upa.getUsername(), upa.getPassword());
	  List<Authority> authorities = new ArrayList<Authority>();
	  //		authorities.add(new ConcurrentLoginPermission(10,10));
	  user.setAuthorities(authorities);
	  }
	  return user;
	  }*/
	return null;
    }

    public 	void delete(java.lang.String username) {

    }

    public 	boolean doesExist(java.lang.String username) {
	return true;
    }

    public String getAdminName() {
	return "foo";
    }

    public 	String[] getAllUserNames() {
	return new String[]{"jeffmc"};
    }


    public 	org.apache.ftpserver.ftplet.User getUserByName(java.lang.String username) {
	return user;
    }
	
    public 	boolean isAdmin(java.lang.String username) {
	return  false;
    }

    public 	void save(org.apache.ftpserver.ftplet.User user)  {

    }

}


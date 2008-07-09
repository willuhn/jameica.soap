/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/Attic/CxfDeployer.java,v $
 * $Revision: 1.2 $
 * $Date: 2008/07/09 21:39:39 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap;

import java.io.File;

import org.mortbay.jetty.security.UserRealm;

import de.willuhn.jameica.plugin.AbstractPlugin;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.webadmin.Settings;
import de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer;
import de.willuhn.jameica.webadmin.server.JameicaUserRealm;

/**
 * Deployer fuer das CXF-System.
 */
public class CxfDeployer extends AbstractWebAppDeployer
{
  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getContext()
   */
  protected String getContext()
  {
    return "/cxf";
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getPath()
   */
  protected String getPath()
  {
    AbstractPlugin plugin = Application.getPluginLoader().getPlugin(Plugin.class);
    return plugin.getResources().getPath() + File.separator + "webapps" + File.separator + "cxf";
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getSecurityRoles()
   */
  protected String[] getSecurityRoles()
  {
    return Settings.getUseAuth() ? new String[]{"admin"} : null;
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getUserRealm()
   */
  protected UserRealm getUserRealm()
  {
    return Settings.getUseAuth() ? new JameicaUserRealm() : null;
  }

}


/*********************************************************************
 * $Log: CxfDeployer.java,v $
 * Revision 1.2  2008/07/09 21:39:39  willuhn
 * @R Axis2 gegen Apache CXF ersetzt. Letzteres ist einfach besser ;)
 *
 * Revision 1.1  2008/07/09 18:24:34  willuhn
 * @N Apache CXF als zweiten SOAP-Provider hinzugefuegt
 *
 * Revision 1.1  2007/09/29 17:19:43  willuhn
 * @N initial import
 *
 **********************************************************************/
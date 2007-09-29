/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/Attic/SoapDeployer.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/09/29 17:19:43 $
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

import de.willuhn.jameica.plugin.AbstractPlugin;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer;

/**
 * Deployer fuer das Axis2-System.
 */
public class SoapDeployer extends AbstractWebAppDeployer
{
  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getContext()
   */
  protected String getContext()
  {
    return "/axis2";
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getPath()
   */
  protected String getPath()
  {
    AbstractPlugin plugin = Application.getPluginLoader().getPlugin(Plugin.class);
    return plugin.getResources().getPath() + File.separator + "webapps" + File.separator + "axis2";
  }

}


/*********************************************************************
 * $Log: SoapDeployer.java,v $
 * Revision 1.1  2007/09/29 17:19:43  willuhn
 * @N initial import
 *
 **********************************************************************/
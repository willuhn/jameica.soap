/**********************************************************************
 *
 * Copyright (c) by Olaf Willuhn
 * All rights reserved
 * GPLv2
 *
 **********************************************************************/

package de.willuhn.jameica.soap;

import java.io.File;

import org.eclipse.jetty.security.LoginService;

import de.willuhn.jameica.plugin.Manifest;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.webadmin.Settings;
import de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer;
import de.willuhn.jameica.webadmin.server.JameicaLoginService;

/**
 * Deployer fuer das CXF-System.
 * Geht leider nicht direkt als AbstractServletDeployer da dort das URL-Mapping
 * falsch waere.
 */
public class CXFDeployer extends AbstractWebAppDeployer
{
  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getContext()
   */
  protected String getContext()
  {
    return "/soap";
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getPath()
   */
  protected String getPath()
  {
    Manifest mf = Application.getPluginLoader().getManifest(Plugin.class);
    return mf.getPluginDir() + File.separator + "webapps" + File.separator + "soap";
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getSecurityRoles()
   */
  protected String[] getSecurityRoles()
  {
    return Settings.getUseAuth() ? new String[]{"admin"} : null;
  }

  /**
   * @see de.willuhn.jameica.webadmin.deploy.AbstractWebAppDeployer#getLoginService()
   */
  @Override
  protected LoginService getLoginService()
  {
    return Settings.getUseAuth() ? new JameicaLoginService() : null;
  }
}

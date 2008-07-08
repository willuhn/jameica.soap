/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/Plugin.java,v $
 * $Revision: 1.2 $
 * $Date: 2008/07/08 14:27:37 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap;

import org.apache.commons.logging.LogFactory;

import de.willuhn.jameica.plugin.AbstractPlugin;
import de.willuhn.util.ApplicationException;


/**
 * Plugin-Klasse des SOAP-Konnektors
 */
public class Plugin extends AbstractPlugin
{

  /**
   * ct.
   */
  public Plugin()
  {
    super();
  }

  /**
   * @see de.willuhn.jameica.plugin.AbstractPlugin#init()
   */
  public void init() throws ApplicationException
  {
    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",MyLogger.class.getName());
  }

  /**
   * @see de.willuhn.jameica.plugin.AbstractPlugin#install()
   */
  public void install() throws ApplicationException
  {
  }

  /**
   * @see de.willuhn.jameica.plugin.AbstractPlugin#shutDown()
   */
  public void shutDown()
  {
  }

  /**
   * @see de.willuhn.jameica.plugin.AbstractPlugin#update(double)
   */
  public void update(double oldVersion) throws ApplicationException
  {
  }

}


/**********************************************************************
 * $Log: Plugin.java,v $
 * Revision 1.2  2008/07/08 14:27:37  willuhn
 * @C Konstruktor war deprecated
 *
 * Revision 1.1  2007/09/29 17:19:43  willuhn
 * @N initial import
 *
 **********************************************************************/

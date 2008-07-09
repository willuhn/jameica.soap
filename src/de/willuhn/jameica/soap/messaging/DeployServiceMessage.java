/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/messaging/Attic/DeployServiceMessage.java,v $
 * $Revision: 1.1 $
 * $Date: 2008/07/09 23:30:53 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap.messaging;

import de.willuhn.jameica.messaging.Message;


/**
 * Zum Deployen eines Webservice genuegt es, diese
 * Message zu senden.
 */
public class DeployServiceMessage implements Message
{
  private String name = null;
  private Object serviceInstance = null;

  /**
   * ct.
   * @param name Name, unter dem der Service deployed werden soll.
   * @param serviceInstance Instanz des JAX-WS Services.
   */
  public DeployServiceMessage(String name, Object serviceInstance)
  {
    this.name = name;
    this.serviceInstance = serviceInstance;
  }
  
  /**
   * Liefert den Namen des Services.
   * @return Name des Services.
   */
  public String getName()
  {
    return name;
  }

  
  /**
   * Liefert die Service-Instanz.
   * @return the serviceInstance
   */
  public Object getServiceInstance()
  {
    return serviceInstance;
  }

}


/**********************************************************************
 * $Log: DeployServiceMessage.java,v $
 * Revision 1.1  2008/07/09 23:30:53  willuhn
 * @R Nicht benoetigte Jars (gemaess WHICH_JARS) entfernt
 * @N Deployment vereinfacht
 *
 **********************************************************************/

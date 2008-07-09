/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/cxf/services/impl/Attic/Echo.java,v $
 * $Revision: 1.1 $
 * $Date: 2008/07/09 18:24:34 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap.cxf.services.impl;

import javax.jws.WebService;

import de.willuhn.logging.Logger;


/**
 * Implementierung des Echo-Services.
 */
@WebService(endpointInterface = "de.willuhn.jameica.soap.cxf.services.Echo")
public class Echo implements de.willuhn.jameica.soap.cxf.services.Echo
{
  /**
   * @see de.willuhn.jameica.soap.cxf.services.Echo#echo(java.lang.String)
   */
  public String echo(String echo)
  {
    Logger.info("GOT ECHO: " + echo);
    return "Echo: " + echo;
  }

}


/*********************************************************************
 * $Log: Echo.java,v $
 * Revision 1.1  2008/07/09 18:24:34  willuhn
 * @N Apache CXF als zweiten SOAP-Provider hinzugefuegt
 *
 **********************************************************************/
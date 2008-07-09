/**********************************************************************
 * $Source: /cvsroot/jameica/jameica.soap/src/de/willuhn/jameica/soap/services/Echo.java,v $
 * $Revision: 1.1 $
 * $Date: 2008/07/09 21:39:40 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.soap.services;

import javax.jws.WebService;
import javax.jws.WebParam;


/**
 * Interface des Echo-Services.
 */
@WebService
public interface Echo
{
  /**
   * Liefert den uebergebenen Text als Echo zurueck.
   * @param echo Echo-Text.
   * @return Echo.
   */
  public String echo(@WebParam(name="text") String echo);
}


/*********************************************************************
 * $Log: Echo.java,v $
 * Revision 1.1  2008/07/09 21:39:40  willuhn
 * @R Axis2 gegen Apache CXF ersetzt. Letzteres ist einfach besser ;)
 *
 * Revision 1.1  2008/07/09 18:24:34  willuhn
 * @N Apache CXF als zweiten SOAP-Provider hinzugefuegt
 *
 **********************************************************************/
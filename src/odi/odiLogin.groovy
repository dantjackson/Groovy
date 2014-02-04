package odi

import groovy.swing.SwingBuilder
import javax.swing.SwingUtilities
import java.awt.FlowLayout as FL
import javax.swing.DefaultComboBoxModel
import javax.swing.BoxLayout as BXL
import groovy.beans.Bindable;
import oracle.odi.core.persistence.transaction.support.DefaultTransactionDefinition;
import oracle.odi.core.security.Authentication;
import oracle.odi.core.OdiInstance;
import groovy.beans.Bindable
import static javax.swing.JFrame.EXIT_ON_CLOSE
import java.awt.*

// Class to hold a default set of template params to guide the user entry
@Bindable
class TempDefaultConnDetails {

    String url, drv, db_usr, db_pass, odi_usr, odi_pass, work_rep
    String toString() {
        "TempDefaultConnDetails[url=$url,drv=$drv,db_usr=$db_usr,db_pass=$db_pass,odi_usr=$odi_usr,odi_pass=$odi_pass,db_usr=$db_usr,work_rep=$work_rep]"
    }
}

class odiSwingLogin {

    OdiConnection odiConnection;
    TempDefaultConnDetails tempDefaultConnDetails;
    Boolean dialogAction;

    static odiConInputDialog(){

        // Some default Connection details - bound to output for re-use
        def tempDefaultConnDetails = new TempDefaultConnDetails(
                url: 'jdbc:oracle:thin:@127.0.0.1:1521:odidw',
                drv: 'oracle.jdbc.OracleDriver',
                db_usr:    'DEV_ODI_REPO'    ,
                db_pass: 'welcome1'        ,
                odi_usr: 'SUPERVISOR'    ,
                odi_pass: 'SUNOPSIS'    ,
                work_rep: 'WORKREP')

        def swingBuilderInst = new SwingBuilder()

        def dial = swingBuilderInst.dialog(title:'Define ODI Connection Details', locationRelativeTo: null,
        id:'myDialog',modal:true, size: [600, 440]) {
            borderLayout(vgap: 5)
            lookAndFeel('nimbus')  // set look and feel

            panel(constraints: BorderLayout.CENTER,
            border: compoundBorder([
                emptyBorder(10),
                titledBorder('Enter ODI Connection Details:')
            ])) {
                tableLayout {
                    tr {
                        td { label 'URL:'  // text property is default, so it is implicit.
                        }
                        td {
                            textField tempDefaultConnDetails.url, id: 'urlField', columns: 40
                        }
                    }
                    tr {
                        td { label 'Driver:'  }
                        td {
                            textField id: 'drvField', columns: 25, text: tempDefaultConnDetails.drv
                        }
                    }
                    tr {
                        td { label 'DB User:' }
                        td {
                            textField id: 'db_usrField', columns: 20, tempDefaultConnDetails.db_usr
                        }
                    }
                    tr {
                        td { label 'DB Password:' }
                        td {
                            textField id: 'db_passField', columns: 20, tempDefaultConnDetails.db_pass
                        }
                    }

                    tr {
                        td { label 'ODI User:' }
                        td {
                            textField id: 'odi_usrField', columns: 20, tempDefaultConnDetails.odi_usr
                        }
                    }

                    tr {
                        td { label 'ODI Password:' }
                        td {
                            textField id: 'odi_passField', columns: 20, tempDefaultConnDetails.odi_pass
                        }
                    }

                    tr {
                        td { label 'Work Rep:' }
                        td {
                            textField id: 'work_repField', columns: 20, tempDefaultConnDetails.work_rep
                        }
                    }
                    tr {
                        td {
                            label  (
                                    id:'connpic',
                                    icon:imageIcon(url:new java.net.URL('file:img/disconnect.png'))
                                    )
                        }
                        td {
                            label  (id:'connfails',text:'')
                        }
                    }



                }
            }
            panel(constraints: BorderLayout.SOUTH) {
                button id:'connect', text: 'Connect', actionPerformed: {
                    println tempDefaultConnDetails
                    odiConnection = new OdiConnection()
                    odiConnection.odiConnect('src/config/');
                    if (odiConnection!=null) {
                        connfails.text = ""
                        cont.enabled = true;
                        cont.text = 'Can Continue >>';
                        connect.enabled = false;
                        connpic.icon = imageIcon(url:new java.net.URL('file:img/connect.png'))
                    }
                    else {
                        // Failed into the dialog somewhere
                        connfails.text = "Problem Connecting, Please Validate Your Connection Details"
                    }
                }
                button id:'cont', enabled: false, text: 'Continue', actionPerformed: {
                    println tempDefaultConnDetails
                    dialogAction = true;
                    dispose();

                }
                button enabled:true, text: 'Quit', actionPerformed: {
                    dialogAction=false;
                    dispose();
                }
            }

            // Binding of textfield's to address object.
            bean tempDefaultConnDetails,
            url: bind { URLField.text },
            drv: bind { drvField.text },
            db_usr: bind { db_usrField.text }

        }  // swing

        dial.pack()
        dial.show()
    }  // sub
}

//1. Capture User Input, results returned in vars // //
def runLclSwingLogin = new odiSwingLogin()
runLclSwingLogin.odiConInputDialog()
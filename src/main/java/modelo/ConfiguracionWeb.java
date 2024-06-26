package modelo;

import controlador.ControladorPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import vista.VentanaPrincipal;


import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class ConfiguracionWeb {

    //Dejar estas rutas para hacer pruebas
//    private static final String appEnginieria = "C:\\Users\\Ricoh.ses\\Desktop\\AIM P\\CESAforG3.zip";
//    private static final String appAtencionCliente = "C:\\Users\\Ricoh.ses\\Desktop\\AIM P\\D3KX0100E_forEDC.zip";
//    private static final String appMapfreCardCopy = "C:\\Users\\ricoh.ses\\OneDrive - Ricoh Europe PLC\\Desktop\\AIM P\\D3CM5519G_forEDC.zip";
//    private static final String appMapfreCloudHub = "C:\\Users\\ricoh.ses\\OneDrive - Ricoh Europe PLC\\Desktop\\AIM P\\RicohCloudHub-1.3.0_signed.zip";

    private static String directorioActual = new String(System.getProperty("user.dir"));


    private static final String appEnginieria = directorioActual + "\\CESAforG3.zip";
    private static final String appAtencionCliente = directorioActual + "\\D3KX0100E_forEDC.zip";
    private static final String appMapfreCardCopy = directorioActual + "\\D3CM5519G_forEDC.zip";
    private static final String appMapfreCloudHub = directorioActual + "\\RicohCloudHub-1.3.0_signed.zip";


    private static final Logger logger = LogManager.getLogger(ConfiguracionWeb.class);
    public static VentanaPrincipal vistaPrincipal;
    public static Impresora impresora;
    public static WebDriver driver;
    public static ControladorPrincipal controlador;


    public static void principal(Impresora impresoraRecibida, WebDriver driverRecibido, ControladorPrincipal controladorRecibido) {

        //ChromeOptions options = new ChromeOptions();
        //options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        //options.addArguments("--user-data-dir=C:/Users/ricoh.ses/AppData/Local/Google/Chrome/User Data/Default");

        //  BasicConfigurator.configure();  //NO BORRAR
        //  WebDriver driver = new ChromeDriver(); //NO OPTIONS
        System.out.println(appEnginieria);
        //Asignar la vista recibida a la variable dentro la clase
        controlador = controladorRecibido;
        vistaPrincipal = controlador.getVistaPrincipal();
        impresora = impresoraRecibida;
        driver = driverRecibido;
        controlador.valoresTabla.clear();

        //Configuracion generica para todos los modelos
        abrirNavegador();
        identificarModelo();
        crearDatosLista();
        identificarSerial();
        entrarRegistro();
        registro();
        aceptarSeguridad();
        seleccionarConfiguracion();

    }

    public static void seleccionarConfiguracion(){


        vistaPrincipal.getTxtEstado().setText("Selecting configuration");

        //En caso de que sea un tender concreto
        if (impresora.getTender().equals("standard") == false) {

            //Definir los tenders y sus configuraciones

            //El tender es Mapfre
            if (impresora.getTender().equals("mapfre") == true) {

                irHome();
                entrarAddressBook();
                agregarUsuarioAdressBook();
                irHome();
                entrarMenuConfiguration();



                List<String> aplicaciones = new ArrayList<>();

                aplicaciones.add(appMapfreCardCopy);
                aplicaciones.add(appMapfreCloudHub);

                instalarAplicaciones(aplicaciones);


                //Otros tenders
            } else if (impresora.getTender().equals("tender2") == true) {


            }



            //En caso de ser el "tender" standard (configuración por defecto)
        } else {

                //En caso de ser una METIS MF4
                if (impresora.getModelo().equals("IM C2010") || impresora.getModelo().equals("IM C2510") || impresora.getModelo().equals("IM C3010") ||
                        impresora.getModelo().equals("IM C3510") || impresora.getModelo().equals("IM C4510") || impresora.getModelo().equals("IM C5510") ||
                        impresora.getModelo().equals("IM C6010")) {
                    //Configuración METIS MF4

                    entrarMenuConfiguration();
                    activarSpoolPrint();

                    List<String> aplicaciones = new ArrayList<>();
                    aplicaciones.add(appAtencionCliente);
                    aplicaciones.add(appEnginieria);
                    instalarAplicaciones(aplicaciones);

                    if (impresora.getClaveRemote().isEmpty() == false) {
                        activarRemote();
                    }

                    Telnet.telnetScript(impresora);
                    controlador.valoresTabla.replace("Telnet", "OK");

                    //En caso de ser una LUFFY MF1
                } else if (impresora.getModelo().equals("IM 370") || impresora.getModelo().equals("IM 460")) {
                    //Configuración LUFFY MF1
                    entrarAddressBook();
                    eliminarAutoAddressBook();
                    entrarMenuConfiguration();

                    List<String> aplicaciones = new ArrayList<>();
                    aplicaciones.add(appAtencionCliente);

                    if (impresora.getClaveRemote().isEmpty() == false) {
                        activarRemote();
                    }


                    //En caso de no tener una configuración seria solo @Remote
                } else {
                    setFrameMenuConfiguration();
                    if (impresora.getClaveRemote().isEmpty() == false) {
                        activarRemote();
                    }

                }

            }

        }


    public static void abrirNavegador(){


        vistaPrincipal.getTxtEstado().setText("Opening browser");

        //Buscar la IP de la impresora en el navegador web
        driver.get("http://" + impresora.getIp().toString());

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error sleep");
        }

        logger.info("NAVEGADOR ABIERTO");


    }


    public static void entrarRegistro() {

        //Cambiar de frame para poder acceder al login
        //WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[1]"));
        //driver.switchTo().frame(frame);
        // driver.switchTo().frame(0); //Manera alternativa por indice de frame

        vistaPrincipal.getTxtEstado().setText("Entering in login window");

        //Cambiar de frame para poder acceder al login
        driver.switchTo().parentFrame();
        WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[1]"));
        driver.switchTo().frame(frame);


        //Clicar en el login
        WebElement login = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/ul/li[4]/a/span"));
        login.click();

        //Volver al frame padre
        driver.switchTo().parentFrame();


        logger.info(impresora.getSerial() + " - " + impresora.getModelo() + " - ENTRADO EN LA VENTANA DE LOGIN");

    }

    public static void registro() {

        vistaPrincipal.getTxtEstado().setText("Registering at login");

        //Rellenar el nombre de usuario
       // WebElement txtUsuario = driver.findElement(By.xpath("/html/body/table/tbody/tr[6]/td/table/tbody/tr/td[2]/table/tbody/tr[2]/td[2]/input[1]"));
        WebElement txtUsuario = driver.findElement(By.name("userid_work"));
        txtUsuario.sendKeys("admin");

        //Rellenar contraseña
       // WebElement txtPassword = driver.findElement(By.xpath("/html/body/table/tbody/tr[6]/td/table/tbody/tr/td[2]/table/tbody/tr[4]/td[2]/input[1]"));
        WebElement txtPassword = driver.findElement(By.name("password_work"));
        txtPassword.sendKeys("");

        //Clicar en el botón para registrarse
        WebElement btnRegistro = driver.findElement(By.xpath("/html/body/table/tbody/tr[6]/td/table/tbody/tr/td[2]/table/tbody/tr[6]/td[2]/input"));
        btnRegistro.click();

        controlador.valoresTabla.replace("Login", "OK");
        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - USUARIO REGISTRADO");



    }

    public static void aceptarSeguridad() {

        vistaPrincipal.getTxtEstado().setText("Acepting security guide");

        //Clicar el botón de OK para aceptar la seguridad (Es necesario cambiar de frame)
         WebElement frame2 = driver.findElement(By.xpath("/html/frameset/frame[2]"));
         driver.switchTo().frame(frame2);
         WebElement btnSeguridadOk = driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/ul/li[1]/input"));
         btnSeguridadOk.click();

        //Volver al frame padre para seguir con normalidad
        driver.switchTo().parentFrame();

        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - SEGURIDAD ACEPTADA");

    }



    public static void entrarMenuConfiguration(){

        vistaPrincipal.getTxtEstado().setText(" Enter configuration menu");

        Actions action = new Actions(driver);

        //Clicar en el botón de configuación
        //Acceder al frame donde se encuentra el elemento
        driver.switchTo().parentFrame();
        WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[2]"));
        driver.switchTo().frame(frame);
        //Ir al menu Device Management y pulsar el enlace Configuration
        WebElement deviceManagement = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/ul/li[2]/div/a/span[2]"));
        //Situarse encima del menu Device Management
        action.moveToElement(deviceManagement).perform();
        //Clicar configuration
        WebElement configuration = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/ul/li[2]/ul/li[1]/a"));
        configuration.click();

        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - ENTRADO EN EL MENU CONFIGURATION");

    }


    public static void entrarAddressBook(){

        vistaPrincipal.getTxtEstado().setText(" Entering address book menu");

        Actions action = new Actions(driver);

        //En caso de haber aceptado la seguridad es necesario volver al frame padre
        driver.switchTo().parentFrame();

        //Clicar en el botón de configuación
        //Acceder al frame donde se encuentra el elemento
        WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[2]"));
        driver.switchTo().frame(frame);
        //Ir al menu Device Management y pulsar el enlace Configuration
        WebElement deviceManagement = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/ul/li[2]/div/a/span[2]"));
        //Situarse encima del menu Device Management
        action.moveToElement(deviceManagement).perform();
        //Clicar addressBook
        WebElement addressBook = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/ul/li[2]/ul/li[2]/a"));
        addressBook.click();

        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - ENTRADO EN EL MENU ADRESS BOOK");

    }

    public static void eliminarAutoAddressBook(){

        vistaPrincipal.getTxtEstado().setText(" Activating delete auto address");

        WebElement mantenimiento = driver.findElement(By.xpath("/html/body/div/div[1]/div[4]/ul/li[3]/a/nobr"));
        mantenimiento.click();

        WebElement eliminarAuto = driver.findElement(By.xpath("/html/body/div/div/div[2]/form[2]/ul[1]/li/div/dl/dd/label[1]/input"));
        eliminarAuto.click();

        WebElement btnAceptar = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/ul/li[1]/input"));
        btnAceptar.click();

        WebElement btnVolver = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/ul/li/input"));
        btnVolver.click();

        //Volver al frame padre
        driver.switchTo().parentFrame();

        controlador.valoresTabla.replace("Auto Delete Address Book", "OK");
        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - AUTO ADDRESS ACTIVADO");

    }

    public static void activarSpoolPrint() {

        vistaPrincipal.getTxtEstado().setText("Activate spool printing");

        //Clicar en System
        WebElement system = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/div[2]/table[3]/tbody/tr/td[2]/table[2]/tbody/tr/td[3]/table/tbody/tr[3]/td[2]/a"));
        system.click();
        //Clicar en Spool Printing
        WebElement spoolOn = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/form/table[2]/tbody/tr[4]/td[4]/table/tbody/tr/td/input[1]"));
        spoolOn.click();
        //Clicar en el borón OK
        WebElement btnOk = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td/table/tbody/tr/td"));
        btnOk.click();

        //Clicar en el botón OK mientras hace la actualización. Es necesario esperar unos 20 segundos
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            System.out.println("Error sleep");
        }

        WebElement btnUpdateOK = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[2]/tbody/tr/td/form[1]/table[2]/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td"));
        btnUpdateOK.click();

        controlador.valoresTabla.replace("Activate Spool Printing", "OK");
        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - SPOOL PRINT ACTIVADO");
    }

    public static void activarRemote(){

        vistaPrincipal.getTxtEstado().setText("Registering remote");


        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofMinutes(5))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        //Buscar el @remote Setup RC Gate
        //WebElement btnGate = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/div[2]/table[3]/tbody/tr/td[4]/table[5]/tbody/tr/td[3]/table/tbody/tr[3]/td[2]/a"));
        //WebElement btnGate = driver.findElement(By.xpath("//*[text()='Configurar RC Gate']")); //CASTELLANO
        WebElement btnGate = driver.findElement(By.xpath("//*[text()='Setup RC Gate']")); //INGLES
        btnGate.click();
        //Introducir la clave del @remote de la máquina concreta
        WebElement txtClaveRemote = driver.findElement(By.xpath("/html/body/table[4]/tbody/tr/td[2]/form/table[3]/tbody/tr[5]/td[4]/input"));
        txtClaveRemote.sendKeys(impresora.getClaveRemote().toString());
        //Pulsar el botón de confirmar
        WebElement bntConfirm = driver.findElement(By.xpath("/html/body/table[4]/tbody/tr/td[2]/form/table[1]/tbody/tr/td[3]/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td/a"));
        bntConfirm.click();

        //Esperar a que aparezca el botón de OK para pulsarlo
        try {

            WebElement btnPrimerOk = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath("/html/body/table[4]/tbody/tr/td[2]/form/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td/a"));
                }
            });

            btnPrimerOk.click();

        } catch (Exception e) {
            System.out.println("Error wait");
        }

        //Clicar en el botón de programar
        WebElement bntProgram = driver.findElement(By.xpath("/html/body/table[4]/tbody/tr/td[2]/form/table[1]/tbody/tr/td[4]/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td/a"));
        bntProgram.click();

        //Esperar a que aparezca el botón de OK para pulsarlo

        try {

            WebElement btnSegonOk = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath("/html/body/table[4]/tbody/tr/td[2]/form/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td/a"));
                }
            });

            btnSegonOk.click();

        } catch (Exception e) {
            System.out.println("Error wait");
        }


        controlador.valoresTabla.replace("Register remote", "OK");
        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - REMOTE REGISTRADO");

        //Volver al Menu de Configuration
        //WebElement bntVolver = driver.findElement(By.xpath("/html/body/table[4]/tbody/tr/td[2]/form/table[5]/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td"));
        //bntVolver.click();

    }


    public static void instalarAplicaciones(List<String> aplicaciones) {

        vistaPrincipal.getTxtEstado().setText("Activate aplications");

        //Definir un wait
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofMinutes(5))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

       // WebElement bntInstall = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/div[2]/table[3]/tbody/tr/td[4]/table[7]/tbody/tr/td[3]/table/tbody/tr[5]/td[2]/a")); //IMC 300
        WebElement btnInstall = driver.findElement(By.xpath("//*[text()='Install']")); //INGLES
        btnInstall.click();


        //Hacer mientras haya aplicaciones en la lista
        for(int i = 0; i < aplicaciones.size(); i++){

            System.out.println(i);

            //Seleccionar el botón archivo local
             WebElement archivoLocal = driver.findElement(By.id("fileUpload"));
             archivoLocal.click();

            WebElement seleccionarArchivo = driver.findElement(By.name("fileUp"));
            seleccionarArchivo.sendKeys(aplicaciones.get(i).toString());

            //Mostrar lista
            WebElement mostrarLista = driver.findElement(By.xpath("//*[text()='Display Extended Feature List']")); //INGLES
            mostrarLista.click();


            //Esperar a que aparezca la aplicación y clicar el radio button de la mismo
            try {

                WebElement radioApp = wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(By.id("7"));
                    }
                });

                radioApp.click();

            } catch (Exception e) {
                System.out.println("Error wait");
            }

            //Clicar en Install
            //WebElement instalar = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[4]/tbody/tr[2]/td/a"));
            WebElement install = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[4]/tbody/tr[2]/td/a"));
            install.click();

            //Confirmar instalar la aplicación
            //WebElement btnOk = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[4]/tbody/tr[2]/td/a"));
            WebElement btnOk = driver.findElement(By.xpath("//*[text()='OK']"));
            btnOk.click();

            //Esperar a que vuelva a la pantalla pricial de instalar aplicaciones
            try {

                WebElement btnComprovar = wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(By.id("fileUpload")); //Elemento aque aparezca en la página principal
                    }
                });


            } catch (Exception e) {
                System.out.println("Error wait");
            }

        }


        WebElement btnVolver = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td"));
        btnVolver.click();

        controlador.valoresTabla.replace("Install aplications", "OK");
        logger.info(impresora.getSerial() + " - " +impresora.getModelo() + "  - APLICACIONES INSTALADAS");


    }


    public static void identificarSerial(){

        vistaPrincipal.getTxtEstado().setText("Identifying serial number");

        Actions action = new Actions(driver);


        //Clicar en el botón de Status/inquiry
        //Acceder al frame donde se encuentra el elemento
        WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[2]"));
        driver.switchTo().frame(frame);


            //Ir al menu Status/Information y pulsar el enlace Inquiry
            WebElement statusInformation = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/ul/li[1]/div/a/span[2]")); //FUNCIONA OK

            //Situarse encima del menu Device Management
            action.moveToElement(statusInformation).perform();

        if (impresora.getModelo().equals("P C600") || impresora.getModelo().equals("P C800") || impresora.getModelo().equals("P 801") ||
                impresora.getModelo().equals("P 501") || impresora.getModelo().equals("P 502")) {


            WebElement deviceInfo = driver.findElement(By.xpath("//*[text()='Device Info']"));
            deviceInfo.click();

            WebElement numeroSerie = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td[4]"));
            impresora.setSerial(numeroSerie.getText());

            controlador.setNumeroSerie(impresora.getSerial().toString());

        } else {


            //Clicar Inquiry
            WebElement inquiry = driver.findElement(By.xpath("//*[text()='Inquiry']"));
            inquiry.click();

            WebElement numeroSerie = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/table[3]/tbody/tr/td[2]/table[2]/tbody/tr[3]/td[4]"));
            impresora.setSerial(numeroSerie.getText());

            controlador.setNumeroSerie(impresora.getSerial().toString());

        }

        logger.info(impresora.getSerial() + " - " + impresora.getModelo() + " - SERIAL IDENTIFICADO CORRECTAMENTE");

    }



public static void identificarModelo(){

    //WebElement cliente = driver.findElement (By.xpath ("//*[contains(text(),'cliente')]"));
    //WebElement cliente = driver.findElement(By.xpath("//*[text()='Atención al Cliente: 955 310 381']"));
    //cliente.click();
    //System.out.println(cliente.getText());

    vistaPrincipal.getTxtEstado().setText("Identifying model");


    driver.switchTo().parentFrame();

    //Cambiar de frame para poder acceder al modelo
    WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[1]"));
    driver.switchTo().frame(frame);

    //obtener el texto del modelo
    WebElement modelo = driver.findElement (By.xpath ("/html/body/div[1]/div[1]/div/h2"));
    impresora.setModelo(modelo.getText());

    controlador.setModelo(impresora.getModelo().toString());

    logger.info(impresora.getModelo() + " - MODELO IDENTIFICADO CORRECTAMENTE");

    driver.switchTo().parentFrame();

}


public static void setFrameMenuConfiguration(){

    logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - CAMBIANDO DE FRAME");

    WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[2]"));
    driver.switchTo().frame(frame);

    logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - FRAME CAMBIADO CORRECTAMENTE");

}


public static void crearDatosLista(){


        //En caso de ser un tender concreto
        if (impresora.getTender().equals("mapfre")){

            controlador.valoresTabla.put("Login", "NG");
            controlador.valoresTabla.put("Add Address Book User", "NG");
            controlador.valoresTabla.put("Install aplications", "NG");


        }  else if (impresora.getModelo().equals("IM C2010") || impresora.getModelo().equals("IM C2510") || impresora.getModelo().equals("IM C3010") ||
            impresora.getModelo().equals("IM C3510") || impresora.getModelo().equals("IM C4510") || impresora.getModelo().equals("IM C5510") ||
            impresora.getModelo().equals("IM C6010")) {
        //Configuración METIS MF4

        controlador.valoresTabla.put("Login", "NG");
        controlador.valoresTabla.put("Activate Spool Printing", "NG");
        controlador.valoresTabla.put("Install aplications", "NG");

        if (impresora.getClaveRemote().isEmpty() == false) {
            controlador.valoresTabla.put("Register remote", "NG");
        }

        controlador.valoresTabla.put("Telnet", "NG");




        //En caso de ser una LUFFY MF1
    } else if (impresora.getModelo().equals("IM 370") || impresora.getModelo().equals("IM 460")) {


        controlador.valoresTabla.put("Login", "NG");
        controlador.valoresTabla.put("Auto Delete Address Book", "NG");
        controlador.valoresTabla.put("Install aplications", "NG");

        if (impresora.getClaveRemote().isEmpty() == false) {
            controlador.valoresTabla.put("Register remote", "NG");
        }

        controlador.valoresTabla.put("Telnet", "NG");



        //En caso de no tener una configuración seria solo @Remote
    } else {

        controlador.valoresTabla.put("Login", "NG");

        if (impresora.getClaveRemote().isEmpty() == false) {
            controlador.valoresTabla.put("Register remote", "NG");
        }


    }

}



public static void irHome(){

        //Ir al frame de Home
    driver.switchTo().parentFrame();
    WebElement frame = driver.findElement(By.xpath("/html/frameset/frame[1]"));
    driver.switchTo().frame(frame);


    //Clicar en home
    WebElement home = driver.findElement(By.xpath("//*[text()='Home']"));
    home.click();

}


public static void agregarUsuarioAdressBook() {

    vistaPrincipal.getTxtEstado().setText(" adding user in adress book");

    //Definir wait
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofMinutes(5))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);

    //Clicar en opcion detallada
    WebElement detail = driver.findElement(By.xpath("//*[text()='Detail Input']"));
    detail.click();

    //Clicar en Add User
    WebElement addUser = driver.findElement(By.xpath("//*[text()='Add User']"));
    addUser.click();



    //Esperar a que aparezca el panel de añadir usuario detallado
    try {

        WebElement txtNombre = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.name("entryNameIn"));
            }
        });

        //Escribir el nombre
        txtNombre.sendKeys("RGPD");

    } catch (Exception e) {
        System.out.println("Error wait");
    }


    //Escribir en nombre
    WebElement txtKeyDisplay = driver.findElement(By.name("entryDisplayNameIn"));
    txtKeyDisplay.sendKeys("RGPD");

    //Escribir en mail
    WebElement txtMail = driver.findElement(By.name("mailAddressIn"));
    txtMail.sendKeys("clausulasRGPDUE@mapfre.com");


    //Clicar en el boton OK
    WebElement btnOk = driver.findElement(By.xpath("//*[text()='OK']"));
    btnOk.click();

    controlador.valoresTabla.replace("Add Address Book User", "OK");
    logger.info(impresora.getSerial() + " - " +impresora.getModelo() + " - USUARIO ADDRESS BOOK AÑADIDO CORRECTAMENTE");

}

}
package com.example.cucharon;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.swipeDown;

import android.app.UiAutomation;


import androidx.test.InstrumentationRegistry;

import androidx.test.espresso.IdlingResource;

import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;


import Negocio.Service;
import Presentation.IUposteoProducto;

@RunWith(AndroidJUnit4.class)
public class IUposteoProductoTest extends TestCase {

    @Rule
    public ActivityTestRule<IUposteoProducto> activityRule = new ActivityTestRule<>(IUposteoProducto.class);

    private IdlingResource animationIdlingResource;


    @Test
    public void clickPostearProducto() throws InterruptedException {

        String nombre = "prueba";
        onView(withId(R.id.tarjetaEditText)).perform(ViewActions.typeText(nombre));

        //onView(withId(R.id.scrollView2)).perform(swipeDown());
        onView(withId(R.id.posteoBtn)).perform(scrollTo());
        Thread.sleep(2000);

        String descripcion = "Descripcion de la prueba";
        onView(withId(R.id.descripcionEditText)).perform(ViewActions.typeText(descripcion));

        String precio = "12.4";
        onView(withId(R.id.precioEditText)).perform(ViewActions.typeText(precio));

          

        String hora1 = "12:00";
        onView(withId(R.id.horaRecogida1)).perform(ViewActions.typeText(hora1));

        String hora2 = "01:00";
        onView(withId(R.id.horaRecogida2)).perform(ViewActions.typeText(hora2));

        String horaPreparacion = "01:00";
        onView(withId(R.id.horaPreparacionEditText)).perform(ViewActions.typeText(horaPreparacion));

        String cantidadPlatos = "2";
        onView(withId(R.id.cantidadEditText)).perform(ViewActions.clearText());
        onView(withId(R.id.cantidadEditText)).perform(ViewActions.typeText(cantidadPlatos));

        Service service = Service.getService();

        //onView(withId(R.id.posteoBtn)).perform(click());

        Usuario usurioPublicador = new Usuario("prueba@gmail.com","Testing", "testing", "testing","testing", 123456);
        service.crearUsuario(usurioPublicador);

        Producto producto = new Producto(0,nombre,descripcion,Double.valueOf(precio), hora1+" - "+hora2,horaPreparacion,"imagen prueba","direccion prueba",Integer.valueOf(cantidadPlatos) ,new Date(),usurioPublicador,12.2324,34.4675);

            service.crearProducto(producto);

        Producto productoGuardado= service.getProductoById(producto.getIdProducto());
        //service = Service.getService();

        assertNotNull(productoGuardado);
        assertEquals(producto.getIdProducto(), productoGuardado.getIdProducto());

        service.borrarProducto(producto);
    }




}


/*Thread.sleep(5000);

        onView(withId(R.id.botonIrAlmapa)).perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.map)).perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.selecUbi)).perform(click());

        Thread.sleep(6000); */
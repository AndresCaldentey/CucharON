package com.example.cucharon;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import static java.util.regex.Pattern.matches;

import android.view.View;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import Negocio.Service;
import Presentation.IUposteoProducto;

@RunWith(AndroidJUnit4.class)
public class IUposteoProductoTest2 extends TestCase {

    @Rule
    public ActivityTestRule<IUposteoProducto> activityRule = new ActivityTestRule<>(IUposteoProducto.class);

    @Test
    public void testPosteoSinImagenNiMapa() throws InterruptedException {


        String nombre = "prueba";
        onView(withId(R.id.tarjetaEditText)).perform(ViewActions.typeText(nombre));

        onView(withId(R.id.posteoBtn)).perform(scrollTo());

        String descripcion = "Descripcion de la prueba";
        onView(withId(R.id.descripcionEditText)).perform(ViewActions.typeText(descripcion));

        String precio = "12.4";
        onView(withId(R.id.precioEditText)).perform(ViewActions.typeText(precio));

        String hora1 = "12:00";
        onView(withId(R.id.horaRecogida1)).perform(ViewActions.typeText(hora1));

        String hora2 = "13:00";
        onView(withId(R.id.horaRecogida2)).perform(ViewActions.typeText(hora2));

        String horaPreparacion = "01:00";
        onView(withId(R.id.horaPreparacionEditText)).perform(ViewActions.typeText(horaPreparacion));

        String cantidadPlatos = "2";
        onView(withId(R.id.cantidadEditText)).perform(ViewActions.clearText());
        onView(withId(R.id.cantidadEditText)).perform(ViewActions.typeText(cantidadPlatos));

        onView(withId(R.id.posteoBtn)).perform(click());

        IUposteoProducto activity = activityRule.getActivity();
        assertNotNull(activity);

        View view = activity.findViewById(R.id.posteoBtn); // Aquí puedes usar cualquier vista que esté disponible
        assertNotNull(view);

        Service service = Service.getService();
        Producto producto = activity.posteoSinImagenNiMapa(view);
        Producto productoGuardado = service.getProductoById(producto.getIdProducto());
        assertNotNull(productoGuardado);
        assertEquals(producto.getIdProducto(), productoGuardado.getIdProducto());
        service.borrarProducto(producto);


    }

}
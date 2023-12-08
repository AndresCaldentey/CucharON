package com.example.cucharon;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import Negocio.Service;
import Presentation.IUposteoProducto;
import Presentation.IUregistro;

@RunWith(AndroidJUnit4.class)
public class RegistrarseTest extends TestCase {
    @Rule
    public ActivityTestRule<IUregistro> activityRule = new ActivityTestRule<>(IUregistro.class);

    @Test
    public void testRegistroExitoso() throws InterruptedException {
        String nombre = "Hugo";
        onView(withId(R.id.nombreRegistro)).perform(ViewActions.typeText(nombre));

        String apellido = "Orellana";
        onView(withId(R.id.apellidoRegistro)).perform(ViewActions.typeText(apellido));

        String telefono = "1515651651";
        onView(withId(R.id.telefonoRegistro)).perform(ViewActions.typeText(telefono));

        String email = "hugoo@gmail.com";
        onView(withId(R.id.emailRegistro)).perform(ViewActions.typeText(email));

        String direccion = "el puig";
        onView(withId(R.id.direccionRegistro)).perform(ViewActions.typeText(direccion));

        String password = "password";
        onView(withId(R.id.passwordRegistro)).perform(ViewActions.typeText(password));

        String passwordRepetida = "password";
        onView(withId(R.id.passwordRegistro2)).perform(ViewActions.typeText(passwordRepetida));

        onView(withId(R.id.btnRegistro)).perform(click());

        IUregistro activity = activityRule.getActivity();
        assertNotNull(activity);

        View view = activity.findViewById(R.id.btnRegistro); // Aquí puedes usar cualquier vista que esté disponible
        assertNotNull(view);

        Service service = Service.getService();
        activity.onClickRegistrar(view);
        Usuario usuarioRegistrado = service.getUsuarioByEmail("hugoo@gmail.com");
        assertNotNull(usuarioRegistrado);
        assertEquals(usuarioRegistrado.getNombre(), "Hugo");
    }
}

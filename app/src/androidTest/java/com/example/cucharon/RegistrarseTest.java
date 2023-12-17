package com.example.cucharon;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import Negocio.Service;
import Presentation.IUregistro;

@RunWith(AndroidJUnit4.class)
public class RegistrarseTest extends TestCase {
    @Rule
    public ActivityTestRule<IUregistro> activityRule = new ActivityTestRule<>(IUregistro.class);

    @Test
    public void testRegistroExitoso() throws InterruptedException {
        String nombre = "Hugo";
        onView(withId(R.id.nombreRegistro)).perform(ViewActions.clearText());
        onView(withId(R.id.nombreRegistro)).perform(ViewActions.typeText(nombre));

        String apellido = "Orellana";
        onView(withId(R.id.apellidoRegistro)).perform(ViewActions.clearText());
        onView(withId(R.id.apellidoRegistro)).perform(ViewActions.typeText(apellido));

        String telefono = "151565165";
        onView(withId(R.id.telefonoRegistro)).perform(ViewActions.clearText());
        onView(withId(R.id.telefonoRegistro)).perform(ViewActions.typeText(telefono));

        String email = "hugoo@gmail.com";
        onView(withId(R.id.emailRegistro)).perform(ViewActions.clearText());
        onView(withId(R.id.emailRegistro)).perform(ViewActions.typeText(email));

        String direccion = "el puig";
        onView(withId(R.id.direccionRegistro)).perform(ViewActions.clearText());
        onView(withId(R.id.direccionRegistro)).perform(ViewActions.typeText(direccion), pressImeActionButton());

        String password = "Contraseaa12";
        onView(withId(R.id.passwordRegistro)).perform(ViewActions.clearText());
        onView(withId(R.id.passwordRegistro)).perform(ViewActions.typeText(password), pressImeActionButton());

        String passwordRepetida = "Contraseaa12";
        onView(withId(R.id.passwordRegistro2)).perform(ViewActions.clearText());

        onView(withId(R.id.btnRegistro)).perform(click());

        Service service = Service.getService();

        Usuario usuarioRegistrado = service.getUsuarioByEmail("hugoo@gmail.com");
        assertNotNull(usuarioRegistrado);
        assertEquals(usuarioRegistrado.getNombre(), "Hugo");
    }
}

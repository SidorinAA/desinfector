package org.example.instatiation;

import org.example.interfaces.Instatiation;

public class InstatiationBefore implements Instatiation {

    //по сути проверка на прокси или нет объект
    @Override
    public void isInitalize(Object object, ClassLoader classLoader) {
        boolean result = false;
        if (object != null) {
            Class<?> aClass = null;
            try {
                aClass =  Class.forName(object.getClass().getName(), true, classLoader);
            } catch (Exception ex) {
                result = false;
            }

            result = ( aClass == object.getClass() ) ? true : false;
        }

        System.out.println("CLASS IS init=[" + result + "]");
    }
}

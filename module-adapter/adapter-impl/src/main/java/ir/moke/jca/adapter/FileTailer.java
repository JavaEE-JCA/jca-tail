package ir.moke.jca.adapter;

import ir.moke.jca.api.Filter;

import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.regex.PatternSyntaxException;

public class FileTailer extends Thread {

    private final MessageEndpointFactory messageEndpointFactory;
    private final TailActivationSpec spec;
    private final File file;

    public FileTailer(MessageEndpointFactory messageEndpointFactory, TailActivationSpec spec) {
        this.messageEndpointFactory = messageEndpointFactory;
        this.spec = spec;

        String logFile = spec.getFile();
        file = new File(logFile);
    }

    @Override
    public void run() {
        try {
            MessageEndpoint endpoint = messageEndpointFactory.createEndpoint(null);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            /*
             * Skip pointer to end of file and waiting for new lines .
             * */
            bufferedReader.skip(file.length());

            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    /*
                     * TomEE does not implemented getEndpointClass in MessageEndpointFactory ,
                     * so manually implement in  TailActivationSpec
                     * */
                    Class<?> beanClass = spec.getBeanClass() != null ? spec.getBeanClass() : messageEndpointFactory.getEndpointClass();
                    Method[] declaredMethods = beanClass.getDeclaredMethods();
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(Filter.class)) {
                            if (isPatternMatch(line, declaredMethod.getAnnotation(Filter.class).value())) {
                                invoke(endpoint, line, declaredMethod);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invoke(MessageEndpoint endpoint, String line, Method declaredMethod) {
        try {
            endpoint.beforeDelivery(declaredMethod);
            declaredMethod.invoke(endpoint, line);
            endpoint.afterDelivery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isPatternMatch(String line, String regex) {
        boolean patternMatch;
        try {
            patternMatch = line.matches(regex);
        } catch (PatternSyntaxException e) {
            patternMatch = false;
        }
        return patternMatch;
    }
}

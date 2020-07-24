package ir.moke.jca.adapter;

import ir.moke.jca.api.TailListener;

import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

@Activation(messageListeners = TailListener.class)
public class TailActivationSpec implements ActivationSpec {

    public TailActivationSpec() {
        System.out.println("+----------------------------------------+");
        System.out.println("|           TailActivationSpec           |");
        System.out.println("+----------------------------------------+");
    }

    private ResourceAdapter resourceAdapter;
    private String file;
    private Class<?> beanClass ;

    @Override
    public void validate() throws InvalidPropertyException {

    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return this.resourceAdapter;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter resourceAdapter) throws ResourceException {
        this.resourceAdapter = resourceAdapter;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}

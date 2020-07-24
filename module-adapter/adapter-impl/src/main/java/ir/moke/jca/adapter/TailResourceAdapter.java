package ir.moke.jca.adapter;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

@Connector
public class TailResourceAdapter implements ResourceAdapter {

    public TailResourceAdapter() {
        System.out.println("+-----------------------------------------+");
        System.out.println("|           TailResourceAdapter           |");
        System.out.println("+-----------------------------------------+");
    }

    @Override
    public void start(BootstrapContext bootstrapContext) {
    }

    @Override
    public void stop() {
    }

    @Override
    public void endpointActivation(MessageEndpointFactory messageEndpointFactory, ActivationSpec activationSpec) {
        TailActivationSpec tailSpec = (TailActivationSpec) activationSpec;
        new FileTailer(messageEndpointFactory, tailSpec).start();
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory messageEndpointFactory, ActivationSpec activationSpec) {
    }

    @Override
    public XAResource[] getXAResources(ActivationSpec[] activationSpecs) throws ResourceException {
        return new XAResource[0];
    }
}

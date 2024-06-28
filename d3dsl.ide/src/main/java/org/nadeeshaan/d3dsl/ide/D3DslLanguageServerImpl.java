package org.nadeeshaan.d3dsl.ide;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SetTraceParams;
import org.eclipse.xtext.ide.server.LanguageServerImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class D3DslLanguageServerImpl extends LanguageServerImpl {
  @Override
  protected ServerCapabilities createServerCapabilities(InitializeParams params) {
    ServerCapabilities serverCapabilities = super.createServerCapabilities(params);
    CompletionOptions completionProvider = serverCapabilities.getCompletionProvider();
    List<String> triggerCharacters =
        Stream.concat(completionProvider.getTriggerCharacters().stream(), Stream.of(" "))
            .collect(Collectors.toList());
    completionProvider.setTriggerCharacters(triggerCharacters);
    completionProvider.setResolveProvider(true);

    return serverCapabilities;
  }

  @Override
  public void setTrace(SetTraceParams params) {}
}

package dependenceAnalysis.interprocedural;

import dependenceAnalysis.util.Graph;
import dependenceAnalysis.util.Signature;
import util.ReflectionClassReader;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestrictedCallGraph extends CallGraph{

    protected String packagePrefix;
    protected String root;

    /**
     * Constructor, taking as input root-directory (String) for project.
     *
     * @param root
     */
    public RestrictedCallGraph(String root, String packagePrefix) throws ClassNotFoundException {
        super(root);
        this.packagePrefix = packagePrefix;
        lister(root,packagePrefix);
    }

    public void lister(String root, String packagePrefix) throws ClassNotFoundException
    {
        ArrayList<String> line = new ArrayList<String>();
        File dir = new File(root);
        ReflectionClassReader reader = new ReflectionClassReader();
        List<Class<?>> classes = reader.processDirectory(dir, "");
        for (Class c1 : classes) {
            if (!c1.isInterface()) {
                if (c1.toString().contains("$")) {
                    String[] mainClassName = (c1.getName() + "").split("\\$");
                    line.add(mainClassName[0]);
                    line.add("(" + mainClassName[1] + ")");

/**
 * to calculate the number of methods
 */
                    line.add(String.valueOf(c1.getMethods().length));

/**
 * to calculate the number of data-members
 */
                    int fCount=0;
                    Field[] fields = c1.getDeclaredFields();
                    for (Field f: fields){
                        fCount++;
                    }
                    line.add(fCount+"\n");

                }else{
                    int fCount=0;
//                    line.add(c1.getSimpleName());
                    line.add(c1.getName());
                    line.add(String.valueOf(c1.getMethods().length));
                    Field[] fields = c1.getDeclaredFields();
                    for (Field f: fields){
                        fCount++;
                    }
                    line.add(fCount+"\n");
                }
            }
        }
        System.out.println(line);

    }


    public String toString(){
        Graph<Signature> printable = new Graph<Signature>();
        for(Signature sig : callGraph.getNodes()){
            if(!sig.getOwner().startsWith(packagePrefix))
                continue;
            if(isConnected(sig))
                printable.addNode(sig);
        }
        for(Signature sig : printable.getNodes()){
            if(!sig.getOwner().startsWith(packagePrefix))
                continue;
            if(!isConnected(sig))
                continue;
            for(Signature to : callGraph.getSuccessors(sig)){
                if(!to.getOwner().startsWith(packagePrefix))
                    continue;
                if(!isConnected(to))
                    continue;
                printable.addEdge(sig,to);
            }
        }
//        lister(root,packagePrefix);
        return printable.toString();
    }




}

import javax.swing.filechooser.FileFilter;

class TextFileFilter extends FileFilter {
    private final String ext;

    public TextFileFilter(String format) {
        this.ext = format;
    }

    public boolean accept(java.io.File file) {
        if (file.isDirectory()) {
            return true;
        } else
            return (file.getName().endsWith(ext));
        
    }

    public String getDescription() {
        return "*" + ext;
    }
}
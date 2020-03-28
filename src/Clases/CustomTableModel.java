

package Clases;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class CustomTableModel extends DefaultTableModel {
   @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (super.getValueAt(row, column) == null) {
            return "";
        }
        return super.getValueAt(row, column);
    }  
}

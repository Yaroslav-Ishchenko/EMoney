package ua.ishchenko.client.mvp.view.users;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ProvidesKey;
import ua.ishchenko.client.mvp.view.IUsersView;
import ua.ishchenko.common.wsbeans.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class UsersView extends Composite implements IUsersView {
    interface UsersViewUiBinder extends UiBinder<Widget, UsersView> {
    }

    private static UsersViewUiBinder uiBinder = GWT
            .create(UsersViewUiBinder.class);
    //UsersConstants userCnst = GWT.create(UsersConstants.class);
    private IUsersPresenter presenter;
    @UiField(provided = true)
    DataGrid<User> userDataGrid = new DataGrid<User>();

    final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>();
    private ListDataProvider<User> dataProvider;
    ListHandler<User> sortHandler;
    private Column<User, String> idColumn;
    private Column<User, String> nameColumn;
    private Column<User, String> balanceColumn;
    private Column<User, String> createdDateColumn;

    // public static final ProvidesKey<User> KEY_PROVIDER = new
    // ProvidesKey<User>() {
    // @Override
    // public Object getKey(User user) {
    // return user == null ? null : user.getId();
    // }
    // };
    public UsersView() {
        initWidget(uiBinder.createAndBindUi(this));
        // Attach a column sort handler to the ListDataProvider to sort the
        // list.

        userDataGrid.setSelectionModel(selectionModel);
        selectionModel
                .addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
                    public void onSelectionChange(SelectionChangeEvent event) {
                        User selected = selectionModel.getSelectedObject();
                    }
                });
    }

    /**
     * Initialize the columns.
     */
    public void initTableColumns() {

        idColumn = new Column<User, String>(new TextCell()) {
            @Override
            public String getValue(User user) {
                return user.getId() + "";
            }
        };
        userDataGrid.addColumn(idColumn, "ID");
        userDataGrid.setColumnWidth(idColumn, 35, Style.Unit.PX);

        nameColumn = new Column<User, String>(new TextCell()) {
            @Override
            public String getValue(User user) {
                return user.getName() + "";
            }
        };
        userDataGrid.addColumn(nameColumn, "Name");
        userDataGrid.setColumnWidth(nameColumn, 35, Style.Unit.PX);

        balanceColumn = new Column<User, String>(new TextCell()) {
            @Override
            public String getValue(User user) {
                return user.getWallet().getBalance() + "";
            }
        };
        userDataGrid.addColumn(balanceColumn, "Balance");
        userDataGrid.setColumnWidth(balanceColumn, 35, Style.Unit.PX);

        createdDateColumn = new Column<User, String>(new TextCell()) {
            @Override
            public String getValue(User user) {
                return user.getCreatedDate().toLocaleString();
            }
        };
        userDataGrid.addColumn(createdDateColumn, "Created on");
        userDataGrid.setColumnWidth(createdDateColumn, 35, Style.Unit.PX);
    }

    @Override
    public void setDataGridRowData(List<User> userList) {
        initTableColumns();
        //sortHandler = new ListHandler<User>(userList);
        dataProvider = new ListDataProvider<User>();
        dataProvider.setList(userList);
        //sortHandler.setList(dataProvider.getList());
        //userDataGrid.addColumnSortHandler(sortHandler);
        userDataGrid.setRowCount(userList.size());
        userDataGrid.setRowData(userList);
        userDataGrid.setVisibleRange(0, dataProvider.getList().size());
        dataProvider.addDataDisplay(userDataGrid);
        System.out.println(userList.size());
        if (dataProvider.getList().size() > 0) {
            selectionModel.setSelected(dataProvider.getList().get(0), true);
            userDataGrid.setKeyboardSelectedRow(0, false);
        }
        userDataGrid.redraw();
    }

    @Override
    public void setPresenter(IUsersPresenter presenter) {
        this.presenter = presenter;
    }
}

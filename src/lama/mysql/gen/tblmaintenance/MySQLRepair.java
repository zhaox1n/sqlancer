package lama.mysql.gen.tblmaintenance;

import java.util.List;
import java.util.stream.Collectors;

import lama.Query;
import lama.QueryAdapter;
import lama.Randomly;
import lama.mysql.MySQLSchema.MySQLTable;

/**
 * @see https://dev.mysql.com/doc/refman/8.0/en/repair-table.html
 */
public class MySQLRepair {

	private final List<MySQLTable> tables;
	private final StringBuilder sb = new StringBuilder();

	public MySQLRepair(List<MySQLTable> tables) {
		this.tables = tables;
	}

	public static Query repair(List<MySQLTable> tables) {
		return new MySQLRepair(tables).repair();
	}

	// REPAIR [NO_WRITE_TO_BINLOG | LOCAL]
	// TABLE tbl_name [, tbl_name] ...
	// [QUICK] [EXTENDED] [USE_FRM]
	private Query repair() {
		sb.append("REPAIR");
		if (Randomly.getBoolean()) {
			sb.append(" ");
			sb.append(Randomly.fromOptions("NO_WRITE_TO_BINLOG", "LOCAL"));
		}
		sb.append(" TABLE ");
		sb.append(tables.stream().map(t -> t.getName()).collect(Collectors.joining(", ")));
		if (Randomly.getBoolean()) {
			sb.append(" QUICK");
		}
		if (Randomly.getBoolean()) {
			sb.append(" EXTENDED");
		}
		if (Randomly.getBoolean()) {
			sb.append(" USE_FRM");
		}
		return new QueryAdapter(sb.toString());
	}

}
package com.conversor.abstracts;

import com.conversor.conversors.models.CSVFile;
import com.conversor.conversors.models.HTMLFile;
import com.conversor.conversors.models.XMLFile;

public interface IFileConversor {

    public CSVFile convertToCSV();

    public HTMLFile convertToHTML();

    public XMLFile convertToXML();

    public DataFile convertToFormat(FileType fileType);
}

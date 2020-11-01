package dataprocessing;


/**
* dataprocessing/dblp_DataHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from hw2.idl
* Thursday, October 15, 2020 1:50:26 PM CDT
*/

abstract public class dblp_DataHelper
{
  private static String  _id = "IDL:dataprocessing/dblp_Data:1.0";

  public static void insert (org.omg.CORBA.Any a, dataprocessing.dblp_Data that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static dataprocessing.dblp_Data extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (dataprocessing.dblp_DataHelper.id (), "dblp_Data");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static dataprocessing.dblp_Data read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_dblp_DataStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, dataprocessing.dblp_Data value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static dataprocessing.dblp_Data narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof dataprocessing.dblp_Data)
      return (dataprocessing.dblp_Data)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      dataprocessing._dblp_DataStub stub = new dataprocessing._dblp_DataStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static dataprocessing.dblp_Data unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof dataprocessing.dblp_Data)
      return (dataprocessing.dblp_Data)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      dataprocessing._dblp_DataStub stub = new dataprocessing._dblp_DataStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}

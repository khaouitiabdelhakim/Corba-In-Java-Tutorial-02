
/**
* CompteHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from banque.idl
* Friday, September 20, 2024 11:19:23 AM WET
*/

abstract public class CompteHelper
{
  private static String  _id = "IDL:Compte:1.0";

  public static void insert (org.omg.CORBA.Any a, Compte that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Compte extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CompteHelper.id (), "Compte");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Compte read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CompteStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Compte value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Compte narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Compte)
      return (Compte)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _CompteStub stub = new _CompteStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static Compte unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Compte)
      return (Compte)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _CompteStub stub = new _CompteStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}

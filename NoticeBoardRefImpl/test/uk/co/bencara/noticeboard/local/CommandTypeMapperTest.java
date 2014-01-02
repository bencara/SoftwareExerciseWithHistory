/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the CommandTypeMapper functionality
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class CommandTypeMapperTest {
	
	private NoticeBoardCommandType retrievedType;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		retrievedType = null;
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandTypeMapper#getMappedCommandType(java.lang.String)}
	 * for a null command string.
	 */
	@Test
	public void testGetMappedCommandTypeNullCommandString() {
		retrievedType = CommandTypeMapper.getMappedCommandType(null);
		assertEquals("Null command string should return a READ command", NoticeBoardCommandType.READ,  retrievedType);
		
	}
	
	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandTypeMapper#getMappedCommandType(java.lang.String)}
	 * for a empty command string.
	 */
	@Test
	public void testGetMappedCommandTypeEmptyCommandString() {
		retrievedType = CommandTypeMapper.getMappedCommandType("    	");
		assertEquals("Empty command string should return a READ command", NoticeBoardCommandType.READ,  retrievedType);
		
	}
	
	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandTypeMapper#getMappedCommandType(java.lang.String)}
	 * for a post command string check that leading and following whitespace is ignored
	 */
	@Test
	public void testGetMappedCommandTypePostCommandString() {
		retrievedType = CommandTypeMapper.getMappedCommandType("  ->  ");
		assertEquals("A command string  with -> and whitespace should return a POST command", NoticeBoardCommandType.POST,  retrievedType);
		
	}
	
	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandTypeMapper#getMappedCommandType(java.lang.String)}
	 * for a follow command string check that leading and following whitespace is ignored
	 */
	@Test
	public void testGetMappedCommandTypeFollowCommandString() {
		retrievedType = CommandTypeMapper.getMappedCommandType("  follows  ");
		assertEquals("A command string  with follows and whitespace should return a FOLLOW command", NoticeBoardCommandType.FOLLOW,  retrievedType);
		
	}
	
	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandTypeMapper#getMappedCommandType(java.lang.String)}
	 * for a follow command string check that leading and following whitespace is ignored
	 */
	@Test
	public void testGetMappedCommandTypeWallCommandString() {
		retrievedType = CommandTypeMapper.getMappedCommandType("  wall  ");
		assertEquals("A command string  with wall and whitespace should return a WALL command", NoticeBoardCommandType.WALL,  retrievedType);
		
	}
	
	
	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandTypeMapper#getMappedCommandType(java.lang.String)}
	 * for a an unknown command string check that leading and following whitespace is ignored
	 */
	@Test
	public void testGetMappedCommandTypeUnknownCommandString() {
		retrievedType = CommandTypeMapper.getMappedCommandType("  unknown  ");
		assertNull("An unknown command string should return a null command type", retrievedType);
		
	}

}

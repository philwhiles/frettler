package me.flotsam.frettler.engine;

import static me.flotsam.frettler.engine.IntervalPattern.CHORD_5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_7FLAT5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_7SHARP5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_AUG;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_DIM;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN6;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN7FLAT5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_ADD9;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_SUS2;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_SUS4;
import static me.flotsam.frettler.engine.Note.A;
import static me.flotsam.frettler.engine.Note.Ab;
import static me.flotsam.frettler.engine.Note.B;
import static me.flotsam.frettler.engine.Note.Bb;
import static me.flotsam.frettler.engine.Note.C;
import static me.flotsam.frettler.engine.Note.D;
import static me.flotsam.frettler.engine.Note.Db;
import static me.flotsam.frettler.engine.Note.E;
import static me.flotsam.frettler.engine.Note.Eb;
import static me.flotsam.frettler.engine.Note.F;
import static me.flotsam.frettler.engine.Note.G;
import static me.flotsam.frettler.engine.Note.Gb;
import static me.flotsam.frettler.instrument.FrettedInstrument.InstrumentDefinition;
import static me.flotsam.frettler.instrument.FrettedInstrument.InstrumentDefinition.GUITAR_EADGBE;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public class ChordBank {

  //@formatter:off
  private static List<ChordDefinition> entries = Arrays.asList(
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MAJ,"x02220","xx123x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MAJ,"x02220","xx213x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_AUG,"x03221","xx4231"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_7SHARP5,"x03021","xx3x21"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_5,"x02xxx","x2xxxx"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_5,"577xxx","134xxx"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_7,"x02020","xx1x2x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_7,"x02020","xx2x3x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_7,"x02223","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_7,"x02223","xx1112"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_7FLAT5,"x01223","xx1224"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_DIM,"5xx545","2xx314"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_DIM,"x0121x","xx132x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_DIM,"x0121x","xx132x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN,"x02210","xx231x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN7,"x02010","xx2x1x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN7,"x02213","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN7,"x02213","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN7FLAT5,"5x554x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN7FLAT5,"x01213","xx1214"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_MIN7FLAT5,"xx1213","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_SUS2,"5xx455","2xx134"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_SUS2,"x02200","xx12xx"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_SUS2,"x02200","xx12xx"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_SUS4,"x02230","xx123x"),
    new ChordDefinition(GUITAR_EADGBE, A,CHORD_SUS4,"x02230","xx124x"),

    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_MAJ,"43111x","x3111x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_MAJ,"43111x","43111x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_AUG,"032110","x4312x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7SHARP5,"032112","x42113"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7SHARP5,"4x455x","1x234x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7,"xx1112","xx1112"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7,"xx1112","xx1112"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7FLAT5,"4x453x","xx342x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7FLAT5,"4x453x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_7FLAT5,"xx0112","xxx112"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_DIM,"4xx434","2xx314"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_DIM,"x20134","x2x134"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_DIM,"xx0101","xxx102"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_MIN,"x21144","x21144"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_MIN,"xx1444","xx1444"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_MIN7,"4x444x","2x333x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_MIN7FLAT5,"4x443x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_SUS2,"x11144","x11144"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_SUS2,"x111xx","x123xx"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_SUS4,"xx1124","xx1124"),
    new ChordDefinition(GUITAR_EADGBE, Ab,CHORD_SUS4,"xx1124","xx1124"),
    
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MAJ,"x24442","x13331"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MAJ,"x24444","x1333x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_AUG,"x21003","x21xx4"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_AUG,"x25443","x14231"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_7SHARP5,"x21203","x213x4"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_7SHARP5,"x2524x","x1413x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_7,"x21202","x213x4"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_7,"x21202","x213x4"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_7,"x24242","x13141"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_DIM,"x20431","x2x431"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_DIM,"x2343x","x1243x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_DIM,"x2343x","x1243x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN,"x24432","x13421"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN,"x24432","x13421"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN,"x2443x","x1342x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN,"x5443x","x4231x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN7,"x20202","x2x3x4"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN7,"x20202","x1x2x3"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN7,"x24232","x13121"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN7FLAT5,"x20201","x2x3x1"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN7FLAT5,"x20201","x2x3x1"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_MIN7FLAT5,"x2323x","x1324x"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_SUS2,"x24422","x13411"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_SUS2,"x24422","x13411"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_SUS4,"x24400","x134xx"),
    new ChordDefinition(GUITAR_EADGBE, B,CHORD_SUS4,"x24452","x12341"),
    
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MAJ,"x13331","x13331"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MAJ,"x1333x","x1234x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MAJ,"x1333x","x1333x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MAJ,"xx0331","xxx341"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_AUG,"x10332","x1x332"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_AUG,"x14332","x14231"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7SHARP5,"x10232","x1x143"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7SHARP5,"x1413x","x1413x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7,"x13131","x13141"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7,"x13141","x13141"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7,"xx3334","xx1112"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7FLAT5,"x10130","x1x14x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_7FLAT5,"x1213x","x1214x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_DIM,"x12320","x1243x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_DIM,"x1232x","x1243x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_DIM,"x4232x","x3121x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MIN,"x13321","x13421"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MIN,"x13321","x13421"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MIN7,"x13121","x13121"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MIN7,"x13121","x13121"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_MIN7FLAT5,"x12120","x1324x"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_SUS2,"x13311","x13411"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_SUS2,"x13311","x13411"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_SUS4,"x13341","x12341"),
    new ChordDefinition(GUITAR_EADGBE, Bb,CHORD_SUS4,"x13341","x12341"),

    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MAJ,"032010","x32x1x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MAJ,"03555x","x1333x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MAJ,"43111x","43111x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_AUG,"x3211x","x3211x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_AUG,"x3x554","x1x342"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_AUG,"xx2114","xx2114"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7SHARP5,"xx2314","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7,"x32310","x3241x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7,"x32310","x3241x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7,"x35353","x13141"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7FLAT5,"2x231x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7FLAT5,"x3435x","x1324x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_7FLAT5,"x3435x","x1214x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_DIM,"x3421x","x3421x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_DIM,"xx4542","xx2431"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN,"x3101x","x42x1x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN7,"3x131x","3x141x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN7,"x35343","x13121"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN7,"xx1313","xx1314"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN7FLAT5,"2x131x","2x141x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN7FLAT5,"x3434x","x1224x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_MIN7FLAT5,"x3434x","x1213x"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_SUS2,"x30013","x3xx14"),
    new ChordDefinition(GUITAR_EADGBE, C,CHORD_SUS2,"x35533","x13411"),

    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MAJ,"x00232","xxx132"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MAJ,"x00232","xxx132"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MAJ,"xx0232","xxx132"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_AUG,"x5433x","x3211x"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_AUG,"xx0331","xxx231"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_AUG,"xx0332","xxx231"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7SHARP5,"xx0312","xxx312"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_5,"xx02xx","xxx2xx"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_5,"x577xx","xx123x"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7,"x00212","xxx213"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7,"xx0212","xxx213"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7,"xx0212","xxx213"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7,"xx4535","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7FLAT5,"xx0112","xxx113"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_7FLAT5,"xx4534","xx2413"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_DIM,"xx0131","xxx131"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN,"x00231","xxx231"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN,"xx0231","xxx231"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN,"xx0231","xxx231"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN7,"xx0211","xxx211"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN7,"xx0211","xxx211"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN7FLAT5,"4x353x","2x131x"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN7FLAT5,"xx0111","xxx111"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_MIN7FLAT5,"xx0111","xxx111"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_SUS2,"xx0230","xxx13x"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_SUS2,"xx0230","xxx13x"),
    new ChordDefinition(GUITAR_EADGBE, D,CHORD_SUS4,"xx0233","xxx134"),

    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MAJ,"x43121","x43121"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MAJ,"x43121","x43121"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_AUG,"x4322x","x4312x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_AUG,"x4322x","x3211x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7SHARP5,"x43201","x432x1"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7SHARP5,"xx3425","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7,"x4342x","x3241x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7,"xx3424","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7,"xx3424","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7FLAT5,"30342x","xx341x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7FLAT5,"3x342x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_7FLAT5,"x4544x","x1214x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_DIM,"x42020","x41x2x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_DIM,"xx2323","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN,"xx2120","xx213x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN,"xx2130","xx213x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN7,"x42100","x421xx"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN7,"xx2424","xx1314"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN7FLAT5,"x22020","x12x3x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN7FLAT5,"x42000","x31xxx"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_MIN7FLAT5,"x4545x","x1213x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_SUS2,"xx113x","xx113x"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_SUS2,"xx3311","xx3411"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_SUS4,"xx3341","xx2341"),
    new ChordDefinition(GUITAR_EADGBE, Db,CHORD_SUS4,"xxx122","xxx134"),

    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MAJ,"022100","x231xx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_AUG,"032110","x4312x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_AUG,"4xx554","1xx342"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_AUG,"xx2110","xx312x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_7SHARP5,"xx0110","xxx12x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_7SHARP5,"xx2534","xx1423"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_5,"02xxxx","x1xxxx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_5,"022xxx","x12xxx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_7,"020100","x2x1xx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_7,"xx2434","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_7FLAT5,"x10130","x1x24x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_7FLAT5,"xx2334","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_DIM,"x1232x","x1243x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_DIM,"xx2353","xx1243"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN,"022000","x23xxx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN,"xx2453","xx1342"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7,"020000","x2xxxx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7,"020030","x2xx4x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7,"022030","x23x4x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7,"022040","x23x4x"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7,"xx2433","xx1423"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7FLAT5,"xx2333","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_MIN7FLAT5,"xx2333","xx1333"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_ADD9,"024100","x231xx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_SUS2,"x244x0","x134xx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_SUS2,"xx2452","xx1341"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_SUS4,"022200","x234xx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_SUS4,"022200","x234xx"),
    new ChordDefinition(GUITAR_EADGBE, E,CHORD_SUS4,"xx2455","xx1344"),

    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MAJ,"xx1343","xx1243"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MAJ,"xx5343","xx4132"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_AUG,"321003","x21xx4"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_AUG,"xx1003","xx1xx3"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_AUG,"xx5443","xx4231"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_7SHARP5,"x21023","x21x34"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_7,"x4x343","x2x131"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_7,"xx1323","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_7,"xx1323","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_7FLAT5,"xx1223","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_7FLAT5,"xx1224","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_DIM,"xx4242","xx3141"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MIN,"xx1342","xx1342"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MIN,"xx4342","xx3241"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MIN7,"xx1322","xx1423"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MIN7,"xx1322","xx1423"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MIN7FLAT5,"xx1222","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_MIN7FLAT5,"xx1222","xx1333"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_SUS2,"1113x1","x113x1"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_SUS2,"xx1341","xx1341"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_SUS4,"4x134x","3x124x"),
    new ChordDefinition(GUITAR_EADGBE, Eb,CHORD_SUS4,"xx1344","xx1344"),

    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MAJ,"133211","134211"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MAJ,"xx3211","xx3211"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MAJ7,"xx3210","xx321x"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_AUG,"1x3221","xx4231"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_AUG,"xx3221","xx4231"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7SHARP5,"xx1221","xx1231"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_5,"13xxxx","13xxxx"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_5,"133xxx","134xxx"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7,"131211","x31211"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7,"131211","131211"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7,"xx3545","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7FLAT5,"1x120x","xx23xx"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7FLAT5,"xx1201","xx13x2"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_7FLAT5,"xx3445","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_DIM,"xx0101","xxx1x2"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_DIM,"xx3101","xx41x2"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN,"133111","134111"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN7,"131111","131111"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN7,"133141","123141"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN7,"xx1111","xx1111"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN7,"xx3544","xx1423"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN7FLAT5,"xx3444","xx1234"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_MIN7FLAT5,"xx3444","xx1333"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_SUS2,"x33011","x34x11"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_SUS2,"xx3011","xx3x11"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_SUS4,"113311","114311"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_SUS4,"133311","x23411"),
    new ChordDefinition(GUITAR_EADGBE, F,CHORD_SUS4,"133311","123411"),

    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MAJ,"320003","21xxx3"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MAJ,"355433","134211"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_AUG,"321003","321xx4"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_AUG,"3x544x","1x423x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_AUG,"xx5443","xx4231"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_5,"355xxx","134xxx"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_7,"320001","x2xxx1"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_7,"320001","32xxx1"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_7,"353433","131211"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_7FLAT5,"3x342x","xx341x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_7FLAT5,"3x342x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_7FLAT5,"x43001","x43xx1"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_DIM,"x1x023","x1xx23"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN,"310033","x1xx34"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN,"310033","21xx34"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN,"355333","134111"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN6,"xx5330","xx312x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN7,"353333","131111"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN7,"3x333x","2x333x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN7,"x1303x","x13x4x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_MIN7FLAT5,"3x332x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_SUS2,"300033","xxxx34"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_SUS2,"300233","2xx134"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_SUS4,"330013","23xx14"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_SUS4,"335533","114311"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_SUS4,"355533","x23411"),
    new ChordDefinition(GUITAR_EADGBE, G,CHORD_SUS4,"355533","123411"),
    
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MAJ,"244322","x34211"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MAJ,"244322","134211"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_AUG,"xx0332","xxx231"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_AUG,"xx4332","xx4231"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_7SHARP5,"xx4330","xx312x"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_7,"242322","x31211"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_7,"242322","131211"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_7,"xx4320","xx321x"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_7FLAT5,"2x231x","xx341x"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_7FLAT5,"xx4310","xx431x"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_DIM,"2xx212","2xx314"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_DIM,"xx1212","xx1324"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN,"244222","x34111"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN,"244222","134111"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN7,"242222","131111"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN7,"244252","x34141"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN7FLAT5,"2x221x","2x341x"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN7FLAT5,"xx2212","xx2314"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_MIN7FLAT5,"xx4555","xx1333"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_SUS2,"2xx122","xxx134"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_SUS2,"2xx122","2xx134"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_SUS4,"224422","114311"),
    new ChordDefinition(GUITAR_EADGBE, Gb,CHORD_SUS4,"244422","123411")

    
  );
  //@formatter:on
  public static List<ChordDefinition> findChordDefinitions(InstrumentDefinition instrumentDefinition) {
    return entries
        .stream().filter(ce -> ce.getInstrumentDefinition() == instrumentDefinition)
        .collect(Collectors.toList());
  }

  public static List<ChordDefinition> findChordDefinitions(InstrumentDefinition instrumentDefinition,
      Note chordRoot) {
    return entries
        .stream().filter(ce -> ce.getInstrumentDefinition() == instrumentDefinition
            && ce.getChordRoot().getPitch() == chordRoot.getPitch())
        .collect(Collectors.toList());
  }

  public static List<ChordDefinition> findChordDefinitions(InstrumentDefinition instrumentDefinition,
      Note chordRoot, IntervalPattern chordPattern) {
    return entries
        .stream().filter(ce -> ce.getInstrumentDefinition() == instrumentDefinition
            && ce.getChordRoot().getPitch() == chordRoot.getPitch() && ce.getChordPattern() == chordPattern)
        .collect(Collectors.toList());
  }

  public static class ChordDefinition {
    @Getter private InstrumentDefinition instrumentDefinition;
    @Getter private Note chordRoot;
    @Getter private IntervalPattern chordPattern;
    @Getter private List<String> strings;
    @Getter private List<String> fingers;

    public ChordDefinition(InstrumentDefinition instrumentDefinition, Note chordRoot,
        IntervalPattern chordPattern, String strings, String fingers) {
      this.instrumentDefinition = instrumentDefinition;
      this.chordRoot = chordRoot;
      this.chordPattern = chordPattern;
      this.strings = Arrays.asList(strings.split(""));
      this.fingers = Arrays.asList(fingers.split(""));
    }

  }
}

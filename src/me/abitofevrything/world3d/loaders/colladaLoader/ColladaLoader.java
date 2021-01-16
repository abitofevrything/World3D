package me.abitofevrything.world3d.loaders.colladaLoader;

import me.abitofevrything.world3d.loaders.dataStructures.AnimatedModelData;
import me.abitofevrything.world3d.loaders.dataStructures.AnimationData;
import me.abitofevrything.world3d.loaders.dataStructures.MeshData;
import me.abitofevrything.world3d.loaders.dataStructures.SkeletonData;
import me.abitofevrything.world3d.loaders.dataStructures.SkinningData;
import me.abitofevrything.world3d.loaders.xmlParser.XmlNode;
import me.abitofevrything.world3d.loaders.xmlParser.XmlParser;
import me.abitofevrything.world3d.util.ResourceFile;

public class ColladaLoader {

	public static AnimatedModelData loadColladaModel(ResourceFile colladaFile, int maxWeights) {
		XmlNode node = XmlParser.loadXmlFile(colladaFile);

		SkinLoader skinLoader = new SkinLoader(node.getChild("library_controllers"), maxWeights);
		SkinningData skinningData = skinLoader.extractSkinData();

		SkeletonLoader jointsLoader = new SkeletonLoader(node.getChild("library_visual_scenes"), skinningData.jointOrder);
		SkeletonData jointsData = jointsLoader.extractBoneData();

		GeometryLoader g = new GeometryLoader(node.getChild("library_geometries"), skinningData.verticesSkinData);
		MeshData meshData = g.extractModelData();

		return new AnimatedModelData(meshData, jointsData);
	}

	public static AnimationData loadColladaAnimation(ResourceFile colladaFile) {
		XmlNode node = XmlParser.loadXmlFile(colladaFile);
		XmlNode animNode = node.getChild("library_animations");
		XmlNode jointsNode = node.getChild("library_visual_scenes");
		AnimationLoader loader = new AnimationLoader(animNode, jointsNode);
		AnimationData animData = loader.extractAnimation();
		return animData;
	}

}

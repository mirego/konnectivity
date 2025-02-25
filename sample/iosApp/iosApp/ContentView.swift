import SwiftUI
import shared

struct ContentView: View {

    @ObservedObject var networkStateImageResource = ObservableFlowWrapper<KonnectivityImageResource>(Bootstrap().networkStateImageResource)
    @ObservedObject var networkStateText = ObservableFlowWrapper<NSString>(Bootstrap().networkStateText)

	var body: some View {
        VStack(alignment: .center, spacing: 8) {
            if let imageResource = networkStateImageResource.value {
                Image(systemName: imageResource.systemImageName)
                    .renderingMode(.template)
                    .foregroundColor(.blue)
                    .font(.largeTitle)
            }

            if let text = networkStateText.value as? String {
                Text(text)
                    .font(.largeTitle)
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

private extension KonnectivityImageResource {
    var systemImageName: String {
        switch self {
        case .networkStateReachable:
            return "checkmark.icloud.fill"
        case .networkStateReachableMetered:
            return "exclamationmark.icloud.fill"
        case .networkStateUnreachable:
            return "icloud.slash"
        default:
            fatalError("Could not find systemImageName for ImageResource: \(self)")
        }
    }
}

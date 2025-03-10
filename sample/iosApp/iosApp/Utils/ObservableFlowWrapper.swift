import Foundation
import shared

public class ObservableFlowWrapper<T: AnyObject>: ObservableObject {

    @Published public private(set) var value: T?

    private var watcher: Closeable?

    public init(_ flow: CFlow<T>, initial value: T? = nil) {
        self.value = value

        watcher = flow.watch { [weak self] t in
            self?.value = t
        }
    }

    deinit {
        watcher?.close()
    }
}
